package com.example.fetchapp.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import com.example.fetchapp.databinding.ItemGroupBinding
import com.example.fetchapp.databinding.ItemListBinding
import com.example.fetchapp.model.Item


class ItemExpandableListAdapter(
    private val context: Context
) : BaseExpandableListAdapter() {
    private var listDataHeader: List<Int> = emptyList()
    private var listDataChild: Map<Int, List<Item>> = emptyMap()
    private lateinit var binding: ItemListBinding

    override fun getGroupCount(): Int {
        return listDataHeader.size
    }

    override fun getChildrenCount(groupPosition: Int): Int {
        return listDataChild[listDataHeader[groupPosition]]?.size ?: 0
    }

    override fun getGroup(groupPosition: Int): Int {
        return listDataHeader[groupPosition]
    }

    override fun getChild(groupPosition: Int, childPosition: Int): Item? {
        return listDataChild[listDataHeader[groupPosition]]?.get(childPosition)
    }

    override fun getGroupId(groupPosition: Int): Long {
        return groupPosition.toLong()
    }

    override fun getChildId(groupPosition: Int, childPosition: Int): Long {
        return childPosition.toLong()
    }

    override fun hasStableIds(): Boolean {
        return false
    }

    override fun getGroupView(
        groupPosition: Int,
        isExpanded: Boolean,
        convertView: View?,
        parent: ViewGroup?
    ): View {
        val binding =
            ItemGroupBinding.inflate(LayoutInflater.from(parent?.context ?: context), parent, false)
        binding.txtListID.text = buildString {
            append("List ID: ")
            append(getGroup(groupPosition))
        }
        return binding.root
    }

    override fun getChildView(
        groupPosition: Int,
        childPosition: Int,
        isLastChild: Boolean,
        convertView: View?,
        parent: ViewGroup?
    ): View {
        binding =
            ItemListBinding.inflate(LayoutInflater.from(parent?.context ?: context), parent, false)
        binding.txtName.text = listDataChild.values.elementAt(groupPosition)[childPosition].name
        if (childPosition % 2 == 0) {
            binding.layout.setBackgroundColor(Color.LTGRAY)
        } else {
            binding.layout.setBackgroundColor(Color.WHITE)
        }
        return binding.root
    }

    override fun isChildSelectable(groupPosition: Int, childPosition: Int): Boolean {
        return false
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(newGroupedItems: Map<Int, List<Item>>) {
        listDataHeader = newGroupedItems.keys.toList().sorted()
        listDataChild = newGroupedItems
        this.notifyDataSetChanged()
    }
}