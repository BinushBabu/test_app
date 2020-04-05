package com.example.testbase.ui.home.today_task

import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.graphics.drawable.DrawableCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.testbase.R
import com.example.testbase.data.Todo
import kotlinx.android.synthetic.main.holder_categories.view.*


class TodayTaskAdapter(
    private val listToDay: ArrayList<Todo>

) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val viewPool = RecyclerView.RecycledViewPool()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ToDoViewHolder {
        val v =
            LayoutInflater.from(parent.context).inflate(R.layout.holder_categories, parent, false)
        return ToDoViewHolder(v)
    }

    override fun getItemCount() = listToDay.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = listToDay[position]

        holder.itemView.holder_card_categories_back.setCardBackgroundColor(Color.parseColor(item.color_bg))
        holder.itemView.holder_tv_categories_header.text = item.categories
        holder. itemView.holder_tv_categories_header.setTextColor(Color.parseColor(item.color_text))
        holder.itemView.holder_tv_categories_date.text=item.date
        holder.itemView. holder_tv_categories_date .setTextColor(Color.parseColor(item.color_text))
        val wrappedDrawable : Drawable = AppCompatResources.getDrawable(holder.itemView.context, R.drawable.ic_more_vert_black_24dp)!!
        DrawableCompat.setTint(wrappedDrawable, Color.parseColor(item.color_text))
        holder.itemView.holder_categories_img_menu.setImageDrawable(wrappedDrawable)

        val childLayoutManager = LinearLayoutManager(holder.itemView.holder_categories_rc_task.context, RecyclerView.VERTICAL, false)
        holder. itemView.holder_categories_rc_task.apply {
            layoutManager = childLayoutManager
            adapter = TodayTaskSubAdapter(item.taskList)
            setRecycledViewPool(viewPool)
        }

    }

    inner class ToDoViewHolder(v: View) : RecyclerView.ViewHolder(v) {

    }

}