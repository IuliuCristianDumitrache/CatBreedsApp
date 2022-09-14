package com.example.cat.ui.cats

import androidx.recyclerview.widget.DiffUtil
import com.example.cat.models.CatModel

class CatDiffCallback : DiffUtil.ItemCallback<CatModel>() {

    override fun areItemsTheSame(oldItem: CatModel, newItem: CatModel): Boolean =
        oldItem::class.java.simpleName == newItem::class.java.simpleName

    override fun areContentsTheSame(oldItem: CatModel, newItem: CatModel): Boolean =
        oldItem == newItem
}
