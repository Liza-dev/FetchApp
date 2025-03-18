package com.example.fetchapp

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.fetchapp.model.Item
import com.example.fetchapp.databinding.ItemListBinding

class ItemAdapter : RecyclerView.Adapter<ItemAdapter.GroupViewHolder>() {
    private var groupedItems: Map<String, List<Item>> = emptyMap()

    inner class GroupViewHolder(val binding: ItemListBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GroupViewHolder {
        val binding = ItemListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GroupViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GroupViewHolder, position: Int) {
        val listId = groupedItems.keys.elementAt(position)
        val itemsInGroup = groupedItems[listId]

        "List ID: $listId".also { holder.binding.txtListID.text = it }
        val itemNames = itemsInGroup?.joinToString("\n") { "- ${it.name}" } ?: ""
        holder.binding.txtItem.text = itemNames
    }

    override fun getItemCount(): Int {
        return groupedItems.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(newGroupedItems: Map<String, List<Item>>) {
        groupedItems = newGroupedItems
        this.notifyDataSetChanged()
    }
}