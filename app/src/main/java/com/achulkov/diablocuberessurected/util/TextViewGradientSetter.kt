package com.achulkov.diablocuberessurected.util

import android.graphics.LinearGradient
import android.graphics.Shader
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import com.achulkov.diablocuberessurected.R
import javax.inject.Inject
import javax.inject.Singleton


/**
 * Sets red-to-orange gradient as text color for textView
 *  @param view TextView to set gradient on
 */
@Singleton
class TextViewGradientSetter @Inject constructor() {
    fun setTextViewGradient(view: TextView){
        view.paint.shader = LinearGradient(0f, 0f, view.paint.measureText(view.text.toString()), view.textSize,
            ResourcesCompat.getColor(view.context.resources, R.color.red, null),
            ResourcesCompat.getColor(view.context.resources, R.color.orange, null),
            Shader.TileMode.CLAMP)
    }
}