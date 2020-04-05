package com.example.testbase.ui.home.task


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.testbase.R
import com.example.testbase.base.BaseFrag
import kotlinx.android.synthetic.main.fragment_task.*
import noman.weekcalendar.listener.OnDateClickListener
import org.joda.time.*


class TaskFragment : BaseFrag<TaskViewModel>() {


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_task, container, false)
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        inItViews()
    }
    private  fun inItViews(){


        weekCalendar.setOnDateClickListener(OnDateClickListener() {
            fun onDateClick(dateTime: DateTime) {
                task_tv_month.text=dateTime.toString("MMYYYY")
                }
        })
    }
}
