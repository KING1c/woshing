//package com.example.myapplication.misc
//
//import android.util.Log
//import android.view.LayoutInflater
//import android.view.ViewGroup
//import androidx.navigation.findNavController
//import androidx.recyclerview.widget.DiffUtil
//import androidx.recyclerview.widget.ListAdapter
//import androidx.recyclerview.widget.RecyclerView
//
//class StudentDiffCallback : DiffUtil.ItemCallback<WashService>(){
//    override fun areItemsTheSame(oldItem: WashService, newItem: WashService): Boolean {
//        return oldItem.id==newItem.id
//    }
//
//    override fun areContentsTheSame(oldItem: WashService, newItem: WashService): Boolean {
//        return  oldItem == newItem
//    }
//
//}
//class StudentViewHolder(private val binding: ItemStudentBinding)
//    : RecyclerView.ViewHolder(binding.root) {
//
//    fun bind(washService: WashService, listener: StudentAdapter.Listener, isTeacher:Boolean) {
//        binding.apply {
//            textViewStudentName.text = washService.name
//            var count = 0
//            var rating = 0
//            washService.subjects.forEach {
//                rating+=it.rating
//                count++
//            }
//            try {
//                rating /= count
//            }catch (exception:Exception){
//               Log.e("Math","Делить на ноль нельзя")
//            }
//            textViewRating.text = rating.toString()
//            buttonRemove.setOnClickListener {
//                listener.remove(washService)
//            }
//
//
//            root.setOnClickListener {
//                it.findNavController().navigate(R.id.action_listStudentFragment_to_listSubjectFragment, ListSubjectFragment.setBundle(washService.id,isTeacher))
//            }
//        }
//    }
//}
//
//class StudentAdapter(private val listener: Listener, private val isTeacher:Boolean): ListAdapter<WashService, StudentViewHolder>(
//    StudentDiffCallback()
//) {
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentViewHolder {
//        val binding = ItemStudentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
//        return StudentViewHolder(binding)
//    }
//    override fun onBindViewHolder(holder: StudentViewHolder, position:Int){
//        holder.bind(getItem(position),listener,isTeacher)
//    }
//    interface Listener{
//        fun remove(washService: WashService)
//    }
//}