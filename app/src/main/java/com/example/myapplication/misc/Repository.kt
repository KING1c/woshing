package com.example.myapplication.misc

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.Firebase
import com.google.firebase.database.database
import java.util.Calendar
import java.util.Date

interface ListenerRepository {
    fun getAll(): LiveData<List<Order>>
    // для заказов
    fun setOrder(orders:List<Order>)
    fun addOrder(user:User, service: Service, date: Date)
    fun setStatus(idOrder: Long, statusOrder: StatusOrder)



}
class RepositoryInMemoryImpl(context: Context): ListenerRepository {
    private var dataClass = DateClass(
        orders = listOf(),
        users = listOf(),
        services = listOf()
    )
    private val data = MutableLiveData(dataClass)
    private val database = Firebase.database.reference
    private fun sync() {
        data.value = dataClass
        database.removeValue()
        database.setValue(dataClass)
    }
    override fun getAll(): LiveData<List<Order>> = data
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

    private fun getNewId():Long {
        var id = 1L
        dataClass.orders.forEach {
            if (it.id == id) id++
        }
        return id
    }

    override fun addOrder(user: User,service: Service, date: Date) {
        dataClass.orders += listOf(Order(
            id = getNewId(),
            user = user,
            service = service,
            statusOrder = StatusOrder.WAITING,
            timeStart = Calendar.getInstance().time
            ))
        sync()
    }

}
class RepositoryViewModal(application: Application): AndroidViewModel(application) {

    private val repository: RepositoryInMemoryImpl = RepositoryInMemoryImpl(application)
    val data = repository.getAll()
    fun setStudent(washServices: List<WashService>) = repository.setStudents(washServices)
    fun addStudent(name: String, password: String) = repository.addStudent(name,password)
    fun addSubject(idStudent: Long, name: String) = repository.addSubject(idStudent,name)
    fun removeStudent(idStudent: Long) = repository.removeStudent(idStudent)
    fun removeSubject(idSubject: Long, idStudent: Long) =repository.removeSubject(idSubject,idStudent)
    fun setRating(idSubject: Long, idStudent: Long, rating: Int) =repository.setRating(idSubject,idStudent,rating)
}