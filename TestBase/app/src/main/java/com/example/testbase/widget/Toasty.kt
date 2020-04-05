package com.example.testbase.widget

import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat

import com.example.testbase.R
import com.example.testbase.utils.AppUtils
import com.example.testbase.utils.dpToPx

class Toasty(context: Context,isError : Boolean = false,time : Int = LENGTH_LONG) : Toast(context) {

    init {
        duration = time

        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val toastView = inflater.inflate(R.layout.wd_toast_view, null)
        if (isError){
            val red = ContextCompat.getColor(context,R.color.errorRed)
            val gd = GradientDrawable(
                GradientDrawable.Orientation.TOP_BOTTOM,
                intArrayOf(
                    red,
                    AppUtils.darkenColor(red, 0.8f)
                )
            )
            gd.cornerRadius = context.dpToPx(4)
            toastView.setBackground(gd)
        }else {
            val primaryColor = ContextCompat.getColor(context,R.color.colorPrimary)
            val gd = GradientDrawable(
                GradientDrawable.Orientation.TOP_BOTTOM,
                intArrayOf(
                    primaryColor,
                    AppUtils.darkenColor(primaryColor, 0.8f)
                )
            )
            gd.cornerRadius = context.dpToPx(4)
            toastView.setBackground(gd)
        }
        this.view = toastView
    }


    fun toast(msg: String) {
        view.findViewById<TextView>(R.id.toast).setText(msg)
        show()
    }


    fun showAtTop(msg: String) {
        view.findViewById<TextView>(R.id.toast).setText(msg)
        setGravity(Gravity.TOP or Gravity.CENTER_HORIZONTAL, 0, 0)
        show()
    }

    fun showBelow(v: View, msg: String) {
        view.findViewById<TextView>(R.id.toast).setText(msg)
        setGravity(Gravity.TOP or Gravity.CENTER_HORIZONTAL, 0, (v.y + v.height).toInt())
        show()
    }
}