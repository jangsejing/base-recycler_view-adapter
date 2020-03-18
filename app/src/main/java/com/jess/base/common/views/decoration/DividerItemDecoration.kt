package com.jess.base.common.views.decoration

import android.content.Context
import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ddd.airplane.common.utils.tryCatch

/**
 * RecyclerView Divider 추가
 *
 *
 * @property context
 * @property orientation
 * @property drawableId : 구분선 Drawable
 */
class DividerItemDecoration(
    private val context: Context,
    private val orientation: Int,
    private val drawableId: Int
) :
    RecyclerView.ItemDecoration() {

    companion object {
        val HORIZONTAL = LinearLayoutManager.HORIZONTAL
        val VERTICAL = LinearLayoutManager.VERTICAL
    }

    private var drawable: Drawable? = null

    init {
        checkValid()
    }

    private fun checkValid() {
        if (orientation != HORIZONTAL && orientation != VERTICAL) {
            throw IllegalArgumentException("invalid orientation")
        }

        drawable = ContextCompat.getDrawable(context, drawableId)
        if (drawable == null) {
            throw NullPointerException("null drawable")
        }
    }

    override fun onDraw(c: Canvas, parent: RecyclerView) {
        if (orientation == VERTICAL) {
            drawVertical(c, parent)
        } else {
            drawHorizontal(c, parent)
        }
    }

    private fun drawVertical(c: Canvas, parent: RecyclerView) {
        val left = parent.paddingLeft
        val right = parent.width - parent.paddingRight

        val childCount = parent.childCount
        for (i in 0 until childCount) {
            val child = parent.getChildAt(i)
            val params = child
                .layoutParams as RecyclerView.LayoutParams
            val top = child.bottom + params.bottomMargin
            val bottom = top + drawable!!.intrinsicHeight
            drawable?.setBounds(left, top, right, bottom)
            drawable?.draw(c)
        }
    }

    private fun drawHorizontal(c: Canvas, parent: RecyclerView) {
        val top = parent.paddingTop
        val bottom = parent.height - parent.paddingBottom

        val childCount = parent.childCount
        for (i in 0 until childCount) {
            val child = parent.getChildAt(i)
            val params = child
                .layoutParams as RecyclerView.LayoutParams
            val left = child.right + params.rightMargin
            val right = left + drawable!!.intrinsicHeight
            drawable?.setBounds(left, top, right, bottom)
            drawable?.draw(c)
        }
    }

    override fun getItemOffsets(outRect: Rect, itemPosition: Int, parent: RecyclerView) {
        tryCatch {
            if (orientation == VERTICAL) {
                outRect.set(0, 0, 0, drawable!!.intrinsicHeight)
            } else {
                outRect.set(0, 0, drawable!!.intrinsicWidth, 0)
            }
        }
    }
}