package com.jess.base.presenter.main

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.jess.base.common.base.BaseViewModel
import com.jess.base.data.MainData

class MainViewModel(application: Application) : BaseViewModel(application) {

    // 메인 리스트
    private val _list = MutableLiveData<List<MainData>>()
    val list: LiveData<List<MainData>> get() = _list

    fun setList() {
        val list = mutableListOf<MainData>()
        for (i in 1..50) {
            list.add(MainData("tag", "title $i"))
        }
        _list.value = list
    }
}
