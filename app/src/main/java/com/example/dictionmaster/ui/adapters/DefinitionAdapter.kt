package com.example.dictionmaster.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.dictionmaster.R
import com.example.dictionmaster.domain.models.DefinitionMock
import com.example.dictionmaster.domain.models.FreeDictionaryResponse

class DefinitionAdapter :
    ListAdapter<DefinitionMock, DefinitionAdapter.DefinitionViewHolder>(
        DefinitionDiffUtil()
    ) {

    fun setData(newList: List<DefinitionMock>) {
        submitList(newList)
    }

    private class DefinitionDiffUtil : DiffUtil.ItemCallback<DefinitionMock>() {
        override fun areItemsTheSame(oldItem: DefinitionMock, newItem: DefinitionMock): Boolean {
            return oldItem.definition == newItem.definition
        }

        override fun areContentsTheSame(oldItem: DefinitionMock, newItem: DefinitionMock): Boolean {
            return oldItem == newItem
        }
    }

    inner class DefinitionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(
            definitionMock: DefinitionMock,
            position: Int
        ) {
            itemView.apply {
                findViewById<TextView>(R.id.tvDefinition).text =
                    context.getString(R.string.definition_item, position, definitionMock.definition)
                findViewById<RecyclerView>(R.id.rvExamples).adapter = ExamplesAdapter().apply {
                    setData(definitionMock.examples.map {
                        "• $it"
                    })
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DefinitionViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(
                R.layout.item_definition_layout, parent, false
            )
        return DefinitionViewHolder(view)
    }

    override fun onBindViewHolder(holder: DefinitionViewHolder, position: Int) {
        holder.bind(getItem(position), (position + 1))
    }

}