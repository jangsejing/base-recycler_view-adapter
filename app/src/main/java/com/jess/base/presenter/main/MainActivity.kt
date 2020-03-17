package com.jess.base.presenter.main

import android.os.Bundle
import com.jess.base.R
import com.jess.base.common.base.BaseActivity
import com.jess.base.common.base.BaseRecyclerViewAdapter
import com.jess.base.common.extenstion.showToast
import com.jess.base.data.MainData
import com.jess.base.databinding.MainActivityBinding
import com.jess.base.databinding.MainItemBinding
import kotlinx.android.synthetic.main.main_activity.*

class MainActivity : BaseActivity<MainActivityBinding, MainViewModel>() {

    override val viewModelClass: Class<MainViewModel>
        get() = MainViewModel::class.java

    override val layoutRes: Int
        get() = R.layout.main_activity

    override fun initLayout() {
        rv_main.adapter =
            object : BaseRecyclerViewAdapter<MainData, MainItemBinding>(R.layout.main_item) {

            }.apply {
                setOnItemClickListener { view, data ->
                    showToast(data?.title)
                }
            }
    }

    override fun onCreated(savedInstanceState: Bundle?) {
        viewModel.setList()
    }
}
