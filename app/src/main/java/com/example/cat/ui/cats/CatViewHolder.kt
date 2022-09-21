package com.example.cat.ui.cats

import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.cat.databinding.CatListItemBinding
import com.example.cat.extensions.loadWithUri
import com.example.cat.models.CatModel

class CatViewHolder(private val binding: CatListItemBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(cat: CatModel, listener: CatsAdapter.OnCatItemListener?) {
        ViewCompat.setTransitionName(binding.image, "${cat.catId}${cat.image?.id}")
        ViewCompat.setTransitionName(binding.tvName, "${cat.catId}${cat.name}")
        ViewCompat.setTransitionName(binding.tvDescription, "${cat.catId}${cat.description}")
        ViewCompat.setTransitionName(binding.root, "${cat.catId}")
        binding.image.loadWithUri(cat.image?.url)

        binding.tvName.text = cat.name
        binding.tvDescription.text = cat.description

        binding.root.setOnClickListener {
            listener?.onCatItemClicked(binding, cat)
        }
    }

    fun updateName(name: String) {
        binding.tvName.text = name
    }

    fun updateDescription(description: String) {
        binding.tvDescription.text = description
    }
}