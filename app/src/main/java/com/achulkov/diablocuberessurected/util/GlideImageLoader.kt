package com.achulkov.diablocuberessurected.util

import android.content.Context
import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestBuilder
import com.google.firebase.storage.StorageReference

class GlideImageLoader : ImageLoader {

    override fun load(context: Context, url: String): RequestBuilder<Drawable>{
        return Glide.with(context).load(url)
    }

    override fun load(context: Context, ref : StorageReference ) : RequestBuilder<Drawable>{
        return Glide.with(context).load(ref)
    }

    private inner class GlideRequestBuilder(val requestBuilder: RequestBuilder<Drawable>) : ImageLoader.ImageRequest{
        override fun centerCrop(): RequestBuilder<Drawable> {
            return requestBuilder.centerCrop()
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