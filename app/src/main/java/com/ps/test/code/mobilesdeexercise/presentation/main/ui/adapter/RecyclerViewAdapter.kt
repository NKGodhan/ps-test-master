package com.ps.test.code.mobilesdeexercise.presentation.main.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ps.test.code.mobilesdeexercise.databinding.RecyclerViewItemBinding

class RecyclerViewAdapter(
    private var list: List<String>, private val itemClickListener: RecyclerViewItemClickListener
) : RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: RecyclerViewItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(index: Int) {
            binding.root.setOnClickListener {
                itemClickListener.onItemClicked(index)
            }
        }
    }

    interface RecyclerViewItemClickListener {
        fun onItemClicked(index: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            RecyclerViewItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.tvItemTitle.text = list[position]
        holder.bind(position)
    }

    fun setData(data: List<String>) {
        list = data
        notifyDataSetChanged()
    }
}