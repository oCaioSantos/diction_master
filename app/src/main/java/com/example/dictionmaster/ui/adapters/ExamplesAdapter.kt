package com.example.dictionmaster.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.dictionmaster.R

class ExamplesAdapter :
    ListAdapter<String, ExamplesAdapter.ExamplesViewHolder>(ExamplesDiffUtil()) {

    fun setData(examples: List<String>) {
        submitList(examples)
    }

    inner class ExamplesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(example: String) {
            itemView.findViewById<TextView>(R.id.tvExample).text = example
        }
    }

    private class ExamplesDiffUtil : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ExamplesAdapter.ExamplesViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(
                R.layout.item_example_layout, parent, false
            )
        return ExamplesViewHolder(view)
    }

    override fun onBindViewHolder(holder: ExamplesAdapter.ExamplesViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

}