package com.example.cat.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class ImageModel (
    val id: String? = null,
    val width: Long? = null,
    val height: Long? = null,
    val url: String? = null,
): Parcelable