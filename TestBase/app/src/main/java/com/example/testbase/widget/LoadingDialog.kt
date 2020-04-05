package com.example.testbase.widget

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Window
import android.view.WindowManager
import androidx.core.content.ContextCompat
import com.example.testbase.R
import kotlinx.android.synthetic.main.dg_loading_dialog.*

class LoadingDialog(context: Context) : Dialog(context) {

        init {
            requestWindowFeature(Window.FEATURE_NO_TITLE)
            setCancelable(true)
            window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            setContentView(R.layout.dg_loading_dialog)
            val lp = WindowManager.LayoutParams()
            lp.copyFrom(window?.attributes)
            lp.width = WindowManager.LayoutParams.MATCH_PARENT
            lp.height = WindowManager.LayoutParams.MATCH_PARENT
            window?.attributes = lp
            progressBar.indeterminateDrawable.setColorFilter(
                ContextCompat.getColor(context, R.color.colorPrimary),
                android.graphics.PorterDuff.Mode.MULTIPLY)
        }


        fun toggle(status: Boolean) {
            if (status && !isShowing) show()
            else if (!status)cancel()

        }
}