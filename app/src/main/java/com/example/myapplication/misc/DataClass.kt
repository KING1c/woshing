package com.example.myapplication.misc

import java.util.Date
data class DateClass(
    var orders:List<Order>,
    val users:List<User>,
    val services:List<Service>
)
data class User(
    val id:Long = 0,
    val name:String = "",
    val password:String = "",
    var isAdmin:Boolean = false
)
data class Order(
    val id:Long = 0,
    val user: User,
    val service: Service,
    var statusOrder: StatusOrder,
    val timeStart:Date,
)
data class Service(
    val name: String,
    val cast:Int,
    val time:Int,
)
enum class StatusOrder{
    DONE,
    PROCESSING,
    WAITING
}