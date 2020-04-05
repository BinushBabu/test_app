package com.example.testbase.ui.home

import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.graphics.drawable.DrawableCompat
import androidx.fragment.app.Fragment
import com.example.testbase.R
import com.example.testbase.base.BaseActivity
import com.example.testbase.ui.home.task.TaskFragment
import com.example.testbase.ui.home.today_task.TodayTaskFragment
import com.example.testbase.utils.inTransaction
import com.example.testbase.widget.Toasty
import kotlinx.android.synthetic.main.activity_home_c.*

class HomeActivity : BaseActivity<HomeActVM>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.decorView.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        setContentView(R.layout.activity_home_c)
        hideActionBar()
        selectToDo()
        inItViews()
    }

    private fun inItViews() {
        bottom_bar_daily_task.setOnClickListener { selectToDo() }
        bottom_bar_task_calender.setOnClickListener { selectTask() }
    }

    private fun selectToDo() {

        val enableDrawable: Drawable? =bottom_bar_daily_task.drawable
        DrawableCompat.setTint(bottom_bar_daily_task.drawable, Color.BLACK)
        bottom_bar_daily_task.setImageDrawable(enableDrawable)
        val disableDrawable: Drawable? =bottom_bar_task_calender.drawable
        DrawableCompat.setTint(disableDrawable!!, Color.parseColor("#b1b1b1"))
        bottom_bar_task_calender.setImageDrawable(disableDrawable)

        switchFrag(TodayTaskFragment())
    }

    private fun selectTask() {
        val enableDrawable: Drawable? =bottom_bar_task_calender.drawable
        DrawableCompat.setTint(bottom_bar_task_calender.drawable, Color.BLACK)
        bottom_bar_task_calender.setImageDrawable(enableDrawable)
        val disableDrawable: Drawable? =bottom_bar_daily_task.drawable
        DrawableCompat.setTint(disableDrawable!!, Color.parseColor("#b1b1b1"))
        bottom_bar_daily_task.setImageDrawable(disableDrawable)

        switchFrag(TaskFragment())
    }

    private fun switchFrag(mFragment: Fragment) {
        supportFragmentManager.inTransaction {
            replace(R.id.nav_host_fragment, mFragment)
        }
    }
}