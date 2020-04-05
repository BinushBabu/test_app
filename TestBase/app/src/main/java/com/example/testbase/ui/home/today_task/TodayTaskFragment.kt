package com.example.testbase.ui.home.today_task

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.testbase.R
import com.example.testbase.base.BaseFrag
import com.example.testbase.data.Todo
import kotlinx.android.synthetic.main.fragment_daily_task.*
import java.util.*
import javax.inject.Inject

class TodayTaskFragment : BaseFrag<TodayTaskViewModel>() {

    @Inject
    lateinit var glide: Glide

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_daily_task, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        inItViews()
    }
    private fun inItViews() {
       viewModel.getToDoList().observe(this, Observer { inItToDoRecycler(it) })
    }

   private fun inItToDoRecycler(toDo:ArrayList<Todo>){
       rc_today_task_list.layoutManager = LinearLayoutManager(context)
       rc_today_task_list.adapter = TodayTaskAdapter(toDo)
   }

}