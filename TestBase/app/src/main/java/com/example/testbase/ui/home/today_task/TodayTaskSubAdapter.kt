package com.example.testbase.ui.home.today_task

import android.graphics.Color
import android.graphics.Paint
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.graphics.drawable.DrawableCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.testbase.R
import com.example.testbase.data.Task
import kotlinx.android.synthetic.main.holder_task.view.*


class TodayTaskSubAdapter(private val taskList: ArrayList<Task>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val v =
            LayoutInflater.from(parent.context).inflate(R.layout.holder_task, parent, false)
        return TaskViewHolder(v)
    }

    override fun getItemCount() = taskList.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = taskList[position]

        holder.itemView.holder_task_text.text = item.taskName
        holder.itemView.holder_task_text.setTextColor(Color.parseColor(item.color_bg))
        if (item.status) {
            val wrappedDrawable: Drawable = AppCompatResources.getDrawable(
                holder.itemView.context,
                R.drawable.ic_check_circle_black_24dp
            )!!
            DrawableCompat.setTint(wrappedDrawable, Color.parseColor(item.color_bg))
            holder.itemView.holder_task_icon.setImageDrawable(wrappedDrawable)
            holder.itemView.holder_task_text.setPaintFlags(holder.itemView.holder_task_text.getPaintFlags() or Paint.STRIKE_THRU_TEXT_FLAG)
        } else {
            val wrappedDrawable: Drawable = AppCompatResources.getDrawable(
                holder.itemView.context,
                R.drawable.ic_radio_button_unchecked_black_24dp
            )!!
            DrawableCompat.setTint(wrappedDrawable, Color.parseColor(item.color_bg))
            holder.itemView.holder_task_icon.setImageDrawable(wrappedDrawable)
        }

    }

    inner class TaskViewHolder(v: View) : RecyclerView.ViewHolder(v) {

    }
}