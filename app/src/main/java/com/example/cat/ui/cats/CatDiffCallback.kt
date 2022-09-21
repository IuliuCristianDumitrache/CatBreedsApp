package com.example.cat.ui.cats

import androidx.recyclerview.widget.DiffUtil
import com.example.cat.models.CatModel

class CatDiffCallback : DiffUtil.ItemCallback<CatModel>() {

    enum class ChangePayload {
        ALL, NAME, DESCRIPTION
    }

    override fun areItemsTheSame(oldItem: CatModel, newItem: CatModel): Boolean =
        oldItem.catId == newItem.catId

    override fun areContentsTheSame(oldItem: CatModel, newItem: CatModel): Boolean {
        return when {
            oldItem.countryCode != newItem.countryCode -> { false }
            oldItem.description != newItem.description -> { false }
            else -> true
        }
    }

    override fun getChangePayload(oldItem: CatModel, newItem: CatModel): List<ChangePayload> {
        val changePayloadList = mutableListOf<ChangePayload>()

        val shouldRefreshOnlyName = oldItem.name != newItem.name && oldItem.description == newItem.description
        val shouldRefreshOnlyDescription = oldItem.name == newItem.name && oldItem.description != newItem.description

        when {
            shouldRefreshOnlyName -> {
                changePayloadList.add(ChangePayload.NAME)
            }
            shouldRefreshOnlyDescription -> {
                changePayloadList.add(ChangePayload.DESCRIPTION)
            }
            else -> {
                changePayloadList.add(ChangePayload.ALL)
            }
        }

        return changePayloadList.toList()
    }
}
