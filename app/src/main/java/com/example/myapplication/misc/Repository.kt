package com.example.myapplication.misc

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.time.LocalDateTime
import java.util.Calendar
import java.util.Date

interface ListenerRepository {
    fun getAll(): MutableLiveData<DateClass>
    fun setOrder(orders:List<Order>)
    fun setStatus(idOrder: Long, statusOrder: StatusOrder)
    fun loadUser()
    fun loadOrder()
    fun loadServices()
    fun addOrder(service: Service, date: Date)
}
class RepositoryInMemoryImpl(context: Context): ListenerRepository {
    private var dataClass = DateClass(
        orders = listOf(),
        user = User(),
        services = listOf()
    )
    private val data = MutableLiveData(dataClass)
    private var databaseUsersReference: DatabaseReference = FirebaseDatabase.getInstance().getReference("users")
    private var databaseOrdersReference: DatabaseReference = FirebaseDatabase.getInstance().getReference("orders")
    private var databaseServicesReference: DatabaseReference = FirebaseDatabase.getInstance().getReference("services")
    private val uid:String = Firebase.auth.currentUser!!.uid
    public fun sync() {
        data.value = dataClass
        databaseUsersReference.child(uid).setValue(dataClass.user)
        databaseOrdersReference.child(uid).removeValue()
        databaseOrdersReference.child(uid).setValue(dataClass.orders)
    }
    override fun getAll() = data
    override fun setOrder(orders: List<Order>) {
        dataClass.orders = orders
        sync()
    }

    override fun setStatus(idOrder: Long,statusOrder: StatusOrder) {
        dataClass.orders.map {
            if (it.id==idOrder)
                it.statusOrder = statusOrder
            it
        }
        sync()
    }

        override fun loadUser() {
            val listener = object : ValueEventListener {

                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    dataSnapshot.children.mapNotNull { it.getValue(User::class.java) }.forEach{
                        if (it.uid == uid)
                            dataClass.user = it
                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    // Handle error
                }
            }
            databaseUsersReference.addValueEventListener(listener)
            data.value = dataClass

        }
    override fun loadOrder() {
        val listener = object : ValueEventListener {

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (orderSnapshot in dataSnapshot.child(uid).children){
                     dataClass.orders = dataClass.orders.plus(orderSnapshot.getValue(Order::class.java)!!)
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Handle error
            }
        }
        databaseOrdersReference.addValueEventListener(listener)

        data.value = dataClass
    }
    override fun loadServices() {
        val listener = object : ValueEventListener {

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                dataSnapshot.children.mapNotNull { it.getValue(Service::class.java) }.forEach{
                        dataClass.services = dataClass.services.plus(it)
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Handle error
            }
        }
        databaseServicesReference.addValueEventListener(listener)
        data.value = dataClass
    }

    private fun getNewId():Long {
        var id = 1L
        dataClass.orders.forEach {
            if (it.id == id) id++
        }
        return id
    }

    override fun addOrder(service: Service, date: Date) {
        dataClass.orders += listOf(Order(
            id = getNewId(),
            uidUser = uid,
            service = service,
            statusOrder = StatusOrder.WAITING,
            timeStart = LocalDateTime.now().toString()
            ))
        sync()
    }

}
class RepositoryViewModal(application: Application): AndroidViewModel(application) {

    private val repository: RepositoryInMemoryImpl = RepositoryInMemoryImpl(application)
    val data = repository.getAll()
    fun addOrder(service: Service, date:Date) = repository.addOrder(service,date)
    fun loadUser() = repository.loadUser()
    fun loadOrder() = repository.loadOrder()
    fun loadServices() = repository.loadServices()
}