package com.aswin.kotlinroomappmvvm.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aswin.kotlinroomappmvvm.databinding.ItemRowBinding
import com.aswin.kotlinroomappmvvm.roomDatabase.Item

/**
 * Created by Aswin on 04-10-2024.
 */
class ItemRecyclerAdapter(val context: Context,
                          private var itemList:List<Item>,
                          private val clickListener: OnItemClickListener)
    : RecyclerView.Adapter<ItemRecyclerAdapter.MyViewHolder>()
{
    lateinit var binding : ItemRowBinding

    interface OnItemClickListener {
        fun onItemClick(item: Item)
    }

    //View Holder
    class MyViewHolder(var binding: ItemRowBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Item, clickListener: OnItemClickListener){
            binding.item = item
            binding.executePendingBindings() // Update immediately

            // Set click listener
            binding.root.setOnClickListener {
                clickListener.onItemClick(item)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        binding = ItemRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = itemList[position]
        holder.bind(item, clickListener)
    }

    override fun getItemCount(): Int = itemList.size
    /**OR**/
//    override fun getItemCount(): Int {
//        return journalList.size
//    }

    // Method to update the list and notify the adapter
    fun updateItems(newItems: List<Item>) {
        itemList = newItems
        notifyDataSetChanged()
    }
}