package com.example.cat.models

import android.os.Parcelable
import com.example.cat.data.CatEntity
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class CatModel(
    @SerializedName("id") val catId: String?,
    val name: String?,
    val description: String?,
    @SerializedName("country_code") val countryCode: String?,
    val temperament: String?,
    @SerializedName("wikipedia_url") val wikipediaLink: String?,
    val image: ImageModel?,
): Parcelable {
    fun mapCatModelToCatEntity(): CatEntity {
        return CatEntity(
            catId = catId ?: "",
            name = name ?: "",
            description = description ?: "",
            countryCode = countryCode ?: "",
            temperament = temperament ?: "",
            wikipediaLink = wikipediaLink ?: "",
            imageUrl = image?.url ?: "",
        )
    }
}
