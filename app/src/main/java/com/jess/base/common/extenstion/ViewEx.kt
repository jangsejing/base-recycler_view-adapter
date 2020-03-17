package com.ddd.airplane.common.extension

import android.os.Build
import android.util.TypedValue
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat

/**
 * @author jess
 */
fun View.addRipple() = with(TypedValue()) {
    context.theme.resolveAttribute(android.R.attr.selectableItemBackground, this, true)
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        foreground = ContextCompat.getDrawable(context, resourceId)
    } else {
        setBackgroundResource(resourceId)
    }
}

fun View.addCircleRipple() = with(TypedValue()) {
    context.theme.resolveAttribute(android.R.attr.selectableItemBackgroundBorderless, this, true)
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        foreground = ContextCompat.getDrawable(context, resourceId)
    } else {
        setBackgroundResource(resourceId)
    }
}