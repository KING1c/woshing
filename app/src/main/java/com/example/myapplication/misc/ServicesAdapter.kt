package com.example.myapplication.misc

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.ItemServiceBinding

class ServicesDiffCallback : DiffUtil.ItemCallback<Service>(){
    override fun areItemsTheSame(oldItem: Service, newItem: Service): Boolean {
        return oldItem.name==newItem.name
    }

    override fun areContentsTheSame(oldItem: Service, newItem: Service): Boolean {
        return  oldItem == newItem
    }

}
class ServicesViewHolder(private val binding: ItemServiceBinding)
    : RecyclerView.ViewHolder(binding.root) {

    fun bind(service: Service, listener: ServicesAdapter.Listener, isAdmin:Boolean) {
        binding.apply {
            textViewName.text = service.name
            textViewCast.text = service.cast.toString() + "р"
            textViewTime.text = service.time.toString() + "мин"

            buttonMore.setOnClickListener {

            }
            root.setOnClickListener {
                listener.toOrder(service)
            }
        }
    }
}

class ServicesAdapter(private val listener: Listener, private val isAdmin:Boolean): ListAdapter<Service, ServicesViewHolder>(
    ServicesDiffCallback()
) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ServicesViewHolder {
        val binding = ItemServiceBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ServicesViewHolder(binding)
    }
    override fun onBindViewHolder(holder: ServicesViewHolder, position:Int){
        holder.bind(getItem(position),listener,isAdmin)
    }
    interface Listener{
        fun toOrder(service: Service)
    }
}