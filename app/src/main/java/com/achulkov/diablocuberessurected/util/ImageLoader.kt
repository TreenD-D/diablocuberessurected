package com.achulkov.diablocuberessurected.util

import android.content.Context
import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.bumptech.glide.RequestBuilder
import com.google.firebase.storage.StorageReference

interface ImageLoader {
    fun load(context: Context, url: String): RequestBuilder<Drawable>
    fun load(context: Context, ref: StorageReference): RequestBuilder<Drawable>

    interface ImageRequest {

        fun centerCrop(): RequestBuilder<Drawable>
        fun into(view: ImageView)
        fun placeholder(imageResource: Int): RequestBuilder<Drawable>
        fun error(imageResource: Int): RequestBuilder<Drawable>
    }

}