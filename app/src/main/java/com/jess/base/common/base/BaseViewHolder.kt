package com.jess.base.common.base

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.ddd.airplane.common.utils.tryCatch
import com.jess.base.BR

open class BaseViewHolder<T : Any>(
    val viewDataBinding: ViewDataBinding
) : RecyclerView.ViewHolder(viewDataBinding.root) {

    val view = viewDataBinding.root

    open fun onBind(item: T?) {
        tryCatch {
            viewDataBinding.setVariable(BR.item, item)
            viewDataBinding.executePendingBindings()
        }
    }
}