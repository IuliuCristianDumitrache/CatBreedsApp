package com.example.cat.ui.cats

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.cat.databinding.CatListItemBinding
import com.example.cat.models.CatModel

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

    interface OnCatItemListener {
        fun onCatItemClicked(
            binding: CatListItemBinding,
            cat: CatModel
        )
    }
}