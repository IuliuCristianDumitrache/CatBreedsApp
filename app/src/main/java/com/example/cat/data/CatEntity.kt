package com.example.cat.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.cat.models.CatModel
import com.example.cat.models.ImageModel

@Entity(tableName = "CAT", indices = [Index(value = ["CAT_ID"], unique = true)])
data class CatEntity (
    @PrimaryKey(autoGenerate = true)
    var uid: Long = 0,

    @ColumnInfo(name = "CAT_ID")
    var catId: String = "",

    @ColumnInfo(name = "NAME")
    var name: String = "",

    @ColumnInfo(name = "DESCRIPTION")
    var description: String = "",

    @ColumnInfo(name = "COUNTRY_CODE")
    var countryCode: String = "",

    @ColumnInfo(name = "TEMPERAMENT")
    var temperament: String = "",

    @ColumnInfo(name = "WIKIPEDIA_LINK")
    var wikipediaLink: String = "",

    @ColumnInfo(name = "IMAGE_URL")
    var imageUrl: String = "",
) {
    fun toCatModel(): CatModel {
        return CatModel(catId = catId, name = name, description = description, countryCode = countryCode,
            temperament = temperament, wikipediaLink = wikipediaLink, image = ImageModel(url = imageUrl)
        )
    }
}