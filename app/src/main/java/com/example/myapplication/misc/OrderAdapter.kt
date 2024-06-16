package com.example.myapplication.misc

import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.ItemOrderServiceBinding
import java.time.Duration
import java.time.LocalDateTime
import java.util.Date

class OrderDiffCallback : DiffUtil.ItemCallback<Order>(){
    override fun areItemsTheSame(oldItem: Order, newItem: Order): Boolean {
        return oldItem.id==newItem.id
    }

    override fun areContentsTheSame(oldItem: Order, newItem: Order): Boolean {
        return  oldItem == newItem
    }

}
class OrderViewHolder(private val binding: ItemOrderServiceBinding)
    : RecyclerView.ViewHolder(binding.root) {
    @RequiresApi(Build.VERSION_CODES.O)
    fun bind(order: Order, listener: OrderAdapter.Listener) {
        binding.apply {
            textViewName.text = order.service.name
            val time = Duration.between(LocalDateTime.now(),  LocalDateTime.parse(order.timeStart)).toMinutes() + order.service.time
            if (time > 0) textViewTime.text = time.toString()
            else
                textViewTime.text = "Готов"
        }
    }
}

class OrderAdapter(private val listener: Listener): ListAdapter<Order, OrderViewHolder>(
    OrderDiffCallback()
) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val binding = ItemOrderServiceBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return OrderViewHolder(binding)
    }
    override fun onBindViewHolder(holder: OrderViewHolder, position:Int){
        holder.bind(getItem(position),listener)
    }
    interface Listener{
        fun remove(order: Order)
        fun setRating(order: Order, rating:Int)
    }
}