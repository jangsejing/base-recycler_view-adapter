package com.jess.base.common.base

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.ddd.airplane.common.extension.addCircleRipple
import com.ddd.airplane.common.extension.addRipple

/**
 * Base RecyclerView Adapter for DataBinding
 *
 * @author jess
 * @since 2019-06-07
 */
internal abstract class BaseRecyclerViewAdapter<T : Any, D : ViewDataBinding>(
    @LayoutRes private val layoutId: Int = 0
) : RecyclerView.Adapter<BaseViewHolder<T>>() {

    private val list = mutableListOf<T>()
    private var itemClickListener: ((View, T?) -> Unit)? = null
    private var isCircleRipple: Boolean = false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<T> {

        require(layoutId > 0) { "Empty Layout Resource" }

        val dataBinding = createViewDataBinding(parent, layoutId)
        val viewHolder = createViewHolder(dataBinding)

        dataBinding.run {

            // onClick
            itemClickListener?.let { listener ->
                root.run {
                    if (isCircleRipple) addCircleRipple() else addRipple()
                    setOnClickListener { view ->
                        if (viewHolder.adapterPosition != RecyclerView.NO_POSITION) {
                            listener.invoke(view, list[viewHolder.adapterPosition])
                        }
                    }
                }
            }
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: BaseViewHolder<T>, position: Int) {
        holder.onBind(list[position])
        onBindData(position, list[position], holder.viewDataBinding as D)
    }

    override fun getItemCount(): Int {
        return this.list.size
    }

    fun addAllItem(items: List<T>?) = apply {
        items?.let {
            list.addAll(it)
            notifyDataSetChanged()
        }
    }

    fun addItem(item: T) = apply {
        list.add(item)
        notifyDataSetChanged()
    }

    fun clear() = apply {
        list.clear()
        notifyDataSetChanged()
    }

    fun removeAt(position: Int) {
        list.removeAt(position)
        notifyItemRemoved(position)
    }

    fun getItems(): List<T> {
        return list
    }

    /**
     * 아이템 클릭 리스너
     *
     * @param listener
     * @param isCircleRipple
     */
    open fun setOnItemClickListener(
        isCircleRipple: Boolean = false,
        listener: ((View, T?) -> Unit)
    ) {
        this.itemClickListener = listener
        this.isCircleRipple = isCircleRipple
    }

    open fun createViewHolder(dataBinding: ViewDataBinding): BaseViewHolder<T> {
        return BaseViewHolder(dataBinding)
    }

    open fun createViewDataBinding(parent: ViewGroup, layoutId: Int): ViewDataBinding {
        return DataBindingUtil.inflate<ViewDataBinding>(
            LayoutInflater.from(parent.context),
            layoutId,
            parent,
            false
        )
    }

    open fun onBindData(position: Int, data: T?, dataBinding: D) {

    }

}
