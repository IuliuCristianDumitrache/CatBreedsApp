package com.example.cat.ui.cats

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.cat.databinding.CatListItemBinding
import com.example.cat.models.CatModel
import com.example.cat.ui.cats.CatDiffCallback.*

class CatsAdapter(private val listener: OnCatItemListener?) :
    ListAdapter<CatModel, RecyclerView.ViewHolder>(CatDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = CatListItemBinding.inflate(
            LayoutInflater.from(
                parent.context
            ), parent, false
        )
        return CatViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = currentList[position]
        when (holder) {
            is CatViewHolder -> {
                holder.bind(item, listener)
            }
        }
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        when (holder) {
            is CatViewHolder -> {
                val item = currentList[position]
                if (payloads.isEmpty()) {
                    holder.bind(item, listener)
                    return
                }

                payloads.forEach {
                    val payloadList: List<ChangePayload> = it as List<ChangePayload>
                    payloadList.forEach { currentPayload ->
                        when (currentPayload) {
                            ChangePayload.NAME -> {
                                item.name?.let { name -> holder.updateName(name) }
                            }
                            ChangePayload.DESCRIPTION -> {
                                item.description?.let { description -> holder.updateDescription(description) }
                            }
                            ChangePayload.ALL -> {
                                holder.bind(item, listener)
                            }
                        }
                    }
                }
            }
        }
    }

    interface OnCatItemListener {
        fun onCatItemClicked(
            binding: CatListItemBinding,
            cat: CatModel
        )
    }
}