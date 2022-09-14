package com.example.cat.extensions

import com.bumptech.glide.Glide
import com.google.android.material.imageview.ShapeableImageView

fun ShapeableImageView.loadWithUri(uri: String?) {
    if (uri != null) {
        Glide
            .with(this.context)
            .load(uri)
            .into(this)
    } else {
        this.setImageDrawable(null)
    }
}