package com.example.apparchitecture.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.apparchitecture.databinding.RvItemsBinding
import com.example.apparchitecture.db.table.Note

class MyAdapter(val onItemClick:(Note)->Unit) : ListAdapter<Note, MyAdapter.MyViewHolder>(Companion) {
    inner class MyViewHolder(val binding: RvItemsBinding): RecyclerView.ViewHolder(binding.root)
    companion object:DiffUtil.ItemCallback<Note>() {
        override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
            return oldItem.id==newItem.id
        }

        override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
            return oldItem==newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            RvItemsBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val curr=currentList[position]
        holder.binding.apply {
            tvTitle.text=curr.title
            desc.text=curr.desc
            tvPriority.text=curr.priority.toString()
            root.setOnClickListener {
                onItemClick(curr)
//                callback?.invoke(curr)
            }
        }
    }
//    private var callback:((Note)->Unit)?=null
//    fun setCallback(callbackk:(Note)->Unit){
//        callback=callbackk
//    }
}
