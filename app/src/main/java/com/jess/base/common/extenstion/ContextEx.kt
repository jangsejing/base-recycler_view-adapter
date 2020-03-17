package com.jess.base.common.extenstion

import android.content.Context
import android.widget.Toast
import androidx.annotation.ColorRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat

/**
 * Toast
 *
 * @param stringRes
 */
fun Context.showToast(@StringRes stringRes: Int) {
    Toast.makeText(this, stringRes, Toast.LENGTH_SHORT).show()
}

/**
 * Toast
 *
 * @param text
 */
fun Context.showToast(text: String?) {
    text?.let {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
    }
}