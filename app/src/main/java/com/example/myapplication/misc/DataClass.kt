package com.example.myapplication.misc

import java.sql.Time
import java.time.LocalDateTime
import java.util.Date
data class DateClass(
    var orders:List<Order>,
    var user:User,
    var services:List<Service>
)
data class User(
    val uid:String = "",
    val name:String = "",
    val password:String = "",
    var isAdmin:Boolean = false
)
data class Order(
    val id:Long = 0,
    val uidUser: String = "",
    val service: Service = Service("",0,0),
    var statusOrder: StatusOrder = StatusOrder.WAITING,
    var timeStart:String = LocalDateTime.now().toString(),
)
data class Service(
    val name: String = "",
    val cast:Int = 0,
    val time:Int = 0,
)
enum class StatusOrder{
    DONE,
    PROCESSING,
    WAITING
}