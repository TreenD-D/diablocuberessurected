package com.achulkov.diablocuberessurected.util

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.RequestManager
import com.google.firebase.storage.StorageReference
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GlideImageLoader @Inject constructor(
    private val glide: RequestManager) : ImageLoader {

    override fun load(url: String): RequestBuilder<Drawable>{
        return glide.load(url)
    }

    override fun load(ref : StorageReference ) : RequestBuilder<Drawable>{
        return glide.load(ref)
    }

    override fun clear(view: View) {
        glide.clear(view)
    }

    private inner class GlideRequestBuilder(val requestBuilder: RequestBuilder<Drawable>) : ImageLoader.ImageRequest{
        override fun centerCrop(): RequestBuilder<Drawable> {
            return requestBuilder.centerCrop()
        }

        override fun fitCenter(): RequestBuilder<Drawable> {
            return requestBuilder.fitCenter()
        }

        override fun placeholder(imageResource : Int): RequestBuilder<Drawable> {
            return requestBuilder.placeholder(imageResource)
        }

        override fun error(imageResource : Int): RequestBuilder<Drawable> {
            return requestBuilder.error(imageResource)
        }

        override fun into(view : ImageView){
            requestBuilder.into(view)
        }

    }


}