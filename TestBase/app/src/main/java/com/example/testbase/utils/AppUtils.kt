package  com.example.testbase.utils

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.res.Resources
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.PopupWindow
import androidx.annotation.RequiresApi
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.graphics.drawable.DrawableCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.testbase.BuildConfig
import com.example.testbase.R
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.google.gson.reflect.TypeToken
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


object AppUtils {
    const val RX_JAVA_PLUGIN_ERROR = "rxjava_global_error"

    fun getDateFromYYYYMMDD(date: String?): Date? {
        return if (!date.isNullOrEmpty()) SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(
            date
        )
        else null
    }

    fun getDateFromTDate(date: String?): Date? {
        return if (!date.isNullOrEmpty()) SimpleDateFormat(
            "yyyy-MM-dd'T'HH:mm:ss",
            Locale.getDefault()
        ).parse(date)
        else null
    }

    fun getDropDown(
        context: Context,
        view: View,
        adapter: RecyclerView.Adapter<*>,
        height: Int
    ): PopupWindow {

        val popupWindow: PopupWindow

        val inflater = context
            .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        val layout = inflater.inflate(R.layout.item_drop_down, null)

        val popUpRV = layout.findViewById(R.id.rv) as RecyclerView

        popUpRV.layoutManager = LinearLayoutManager(context)
        popUpRV.adapter = adapter

        popupWindow = PopupWindow(layout, view.measuredWidth, height, true)


        popupWindow.isOutsideTouchable = true
        popupWindow.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        popupWindow.isFocusable = true

        return popupWindow
    }

    fun parseHttpError(error: String?): String? {
        if (error == null) return null
        val json = JSONObject(error)
        val iter = json.keys()
        while (iter.hasNext()) {
            val key = iter.next()
            try {
                var value = json.get(key)
                if (value is JSONArray) value = value[0]
                return "$value"
            } catch (e: JSONException) {

            }

        }
        return null
    }


    fun <T> getJsonObjectFromDataClass(dataObj: T, gson: Gson): JsonObject {
        return gson.fromJson(gson.toJson(dataObj), JsonObject::class.java)
    }

    fun darkenColor(color: Int, factor: Float): Int {
        val a = Color.alpha(color)
        val r = Math.round(Color.red(color) * factor)
        val g = Math.round(Color.green(color) * factor)
        val b = Math.round(Color.blue(color) * factor)
        return Color.argb(a, Math.min(r, 255), Math.min(g, 255), Math.min(b, 255))
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun createChannel(
        channelId: String,
        channelTitle: String,
        notificationManager: NotificationManager
    ) {
        val importance = NotificationManager.IMPORTANCE_HIGH
        val notificationChannel = NotificationChannel(channelId, channelTitle, importance)
        notificationChannel.setShowBadge(true)
        notificationChannel.lockscreenVisibility = Notification.VISIBILITY_PUBLIC
        notificationManager.createNotificationChannel(notificationChannel)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun notificationChannelExists(
        channelId: String,
        notificationManager: NotificationManager
    ): Boolean =
        notificationManager.getNotificationChannel(channelId) != null

    fun getScreenHeight() = Resources.getSystem().displayMetrics.heightPixels

    fun convertMilliSecToTime(millis: Int): String {
        var out = ""
        val minutes = (millis / 1000) / 60
        val seconds = (millis / 1000) % 60
        out = if (minutes > 9) minutes.toString()
        else "0$minutes:"
        if (seconds > 9) out += seconds.toString()
        else out = "${out}0$seconds"
        return out
    }


    fun logD(key: String, v: String) {
        if (BuildConfig.DEBUG) Log.d(key, v)
    }

}