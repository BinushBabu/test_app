package com.example.testbase.ui.home.today_task

import androidx.lifecycle.MutableLiveData
import com.example.testbase.base.BaseViewModel
import com.example.testbase.data.StaticData
import com.example.testbase.data.Todo
import java.util.*
import javax.inject.Inject

class TodayTaskViewModel @Inject constructor() : BaseViewModel() {

    private var mutableNewsList = MutableLiveData<ArrayList<Todo>>()

    fun getToDoList(): MutableLiveData<ArrayList<Todo>> {
        mutableNewsList.value = StaticData.getToDoList()
        return mutableNewsList

    }
}

