package  com.example.testbase.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.net.Uri
import android.text.Spannable
import android.text.TextPaint
import android.text.TextUtils
import android.text.style.URLSpan
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.util.Log
import android.util.Patterns
import android.util.TypedValue
import android.view.ViewTreeObserver
import android.widget.*
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target

import com.example.testbase.R
import com.example.testbase.data.ApiException
import com.example.testbase.data.Database
import com.example.testbase.data.PreferenceStorage
import com.example.testbase.widget.Toasty
import com.google.android.material.tabs.TabLayout
import com.google.android.material.textfield.TextInputLayout
import saschpe.android.customtabs.CustomTabsHelper
import java.lang.Exception
import java.net.*
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Pattern
import androidx.annotation.DrawableRes as DrawableRes1


fun String.isEmail() = Patterns.EMAIL_ADDRESS.matcher(trim()).matches()



fun EditText.getTrimText() = text.toString().trim()

fun Activity.hideKeyboard() {
    // Check if no view has focus:
    try {
        getCurrentFocus()?.let {
            val inputManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputManager.hideSoftInputFromWindow(it.getWindowToken(), 0)
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }

}

fun View.hideKeyboard() {
    try {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
        imm?.hideSoftInputFromWindow(windowToken, 0)
    } catch (e: Exception) {
        e.printStackTrace()
    }
}

fun View.showKeyboard() {
    val inputMethodManager =
        context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    post {
        requestFocus()
        inputMethodManager.showSoftInput(this, 0)
    }
}

fun Activity.setupUI(v: View) {

    if (v is ViewGroup) {
        for (i in 0 until v.childCount) {
            setupUI(v.getChildAt(i))
        }
    }
    if (!(v is EditText)) {

        v.setOnTouchListener { _, _ ->
            hideKeyboard()
            return@setOnTouchListener false
        }
    }

}

fun String.log() = Log.e(javaClass.simpleName, this)

fun Date.toDayMonthYear() = SimpleDateFormat("dd MMMM, yyyy", Locale.getDefault()).format(time)
fun Date.toYearMonthDay() = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(time)
fun Date.toHHmmss() = SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(time)
fun Date.toHHmma() = SimpleDateFormat("hh:mm a", Locale.getDefault()).format(time)
fun Date.toDayMonthYearHrMn() =
    SimpleDateFormat("dd MMM yyyy   hh:mm a", Locale.getDefault()).format(time)

fun Date.toddMMMYYY() = SimpleDateFormat("dd MMM yyyy", Locale.getDefault()).format(time)

fun Context.isLollipop() =
    android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP

fun View.setGone() {
    visibility = View.GONE
}

fun View.setVisible() {
    visibility = View.VISIBLE
}

fun View.setInvisible() {
    visibility = View.INVISIBLE
}


fun String.toast(context: Context?) {
    if (context == null) return
    Toasty(context).toast(this)
}

fun String.errorToast(context: Context?) {
    if (context == null) return
    Toasty(context,true).toast(this)
}

fun String.toastLong(context: Context) = Toast.makeText(context, this, Toast.LENGTH_LONG).show()


inline fun FragmentManager.inTransaction(func: FragmentTransaction.() -> FragmentTransaction) {
    beginTransaction().func().commit()
}


fun TabLayout.wrapTabIndicatorToTitle(externalMargin: Int, internalMargin: Int) {
    val tabStrip = this.getChildAt(0)
    if (tabStrip is ViewGroup) {
        val childCount = tabStrip.childCount
        for (i in 0 until childCount) {
            val tabView = tabStrip.getChildAt(i)
            //set minimum width to 0 for instead for small texts, indicator is not wrapped as expected
            tabView.minimumWidth = 0
            // set padding to 0 for wrapping indicator as title
            tabView.setPadding(0, tabView.paddingTop, 0, tabView.paddingBottom)
            // setting custom margin between tabs
            if (tabView.layoutParams is ViewGroup.MarginLayoutParams) {
                val layoutParams = tabView.layoutParams as ViewGroup.MarginLayoutParams
                when (i) {
                    0 -> // left
                        settingMargin(layoutParams, externalMargin, internalMargin)
                    childCount - 1 -> // right
                        settingMargin(layoutParams, internalMargin, externalMargin)
                    else -> // internal
                        settingMargin(layoutParams, internalMargin, internalMargin)
                }
            }
        }

        this.requestLayout()
    }
}

private fun settingMargin(layoutParams: ViewGroup.MarginLayoutParams, start: Int, end: Int) {
    layoutParams.marginStart = start
    layoutParams.marginEnd = end
    layoutParams.leftMargin = start
    layoutParams.rightMargin = end
}


inline fun View.afterMeasured(crossinline f: View.() -> Unit) {
    viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
        override fun onGlobalLayout() {
            if (measuredWidth > 0 && measuredHeight > 0) {
                viewTreeObserver.removeOnGlobalLayoutListener(this)
                f()
            }
        }
    })
}

fun Context.dpToPx(dp: Number): Float {
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        dp.toFloat(),
        resources.displayMetrics
    )
}

fun Context.pxToSp(dp: Number): Float {
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_SP,
        dp.toFloat(),
        resources.displayMetrics
    )
}

fun ImageView.load(glide: Glide, url: String?, f: () -> Unit) {
    glide.requestManagerRetriever.get(context)
        .load(url)
        .placeholder(ContextCompat.getDrawable(context, R.drawable.round_gray))
        .error(ContextCompat.getDrawable(context, R.drawable.round_gray))
        .apply(RequestOptions.circleCropTransform())
        .listener(object : RequestListener<Drawable> {
            override fun onLoadFailed(
                e: GlideException?,
                model: Any?,
                target: Target<Drawable>?,
                isFirstResource: Boolean
            ): Boolean {
                f()
                return false
            }

            override fun onResourceReady(
                resource: Drawable?,
                model: Any?,
                target: Target<Drawable>?,
                dataSource: DataSource?,
                isFirstResource: Boolean
            ): Boolean {
                f()
                return false
            }

        })
        .into(this)

}

fun ImageView.load(glide: Glide, url: String?, isCircle: Boolean) {
    glide.requestManagerRetriever.get(context)
        .load(url)
        .centerCrop()
        .placeholder(ColorDrawable(ContextCompat.getColor(context, R.color.lightGrey)))
        .error(ColorDrawable(ContextCompat.getColor(context, R.color.lightGrey)))
        .apply { if (isCircle) apply(RequestOptions.circleCropTransform()) }
        .into(this)


}

fun ImageView.loadWithRoundPh(glide: Glide, url: String?) {
    glide.requestManagerRetriever.get(context)
        .load(url)
        .centerCrop()
        .placeholder(ContextCompat.getDrawable(context, R.drawable.round_gray))
        .error(ContextCompat.getDrawable(context, R.drawable.round_gray))
        .apply(RequestOptions.circleCropTransform())
        .into(this)
}

fun ImageView.loadCenterInside(glide: Glide, url: String?) {
    glide.requestManagerRetriever.get(context)
        .load(url)
        .centerCrop()
        .placeholder(ColorDrawable(ContextCompat.getColor(context, R.color.lightGrey)))
        .error(R.drawable.placeholder)
        .apply { apply(RequestOptions.centerInsideTransform()) }
        .into(this)
}

fun ImageView.load(glide: Glide, url: String?) {
    glide.requestManagerRetriever.get(context)
        .load(url)
        .placeholder(ColorDrawable(ContextCompat.getColor(context, R.color.lightGrey)))
        .error(R.drawable.placeholder)
        .apply { apply(RequestOptions.centerInsideTransform()) }
        .into(this)
}

fun ImageView.loadWithOutErrorImg(glide: Glide, url: String?) {
    glide.requestManagerRetriever.get(context)
        .load(url)
        .placeholder(ColorDrawable(Color.TRANSPARENT))
        .error(ColorDrawable(ContextCompat.getColor(context, R.color.lightGrey)))
        .apply { apply(RequestOptions.centerInsideTransform()) }
        .into(this)
}

fun ImageView.loadCenterCropWithOutErrorImg(glide: Glide, url: String?) {
    glide.requestManagerRetriever.get(context)
        .load(url)
        .centerCrop()
        .placeholder(ColorDrawable(ContextCompat.getColor(context, R.color.lightGrey)))
        .error(ColorDrawable(ContextCompat.getColor(context, R.color.lightGrey)))
        .apply { apply(RequestOptions.centerInsideTransform()) }
        .into(this)
}


fun ImageView.loadWithRoundedCorner(glide: Glide, url: String?, radius: Int) {
    glide.requestManagerRetriever.get(context)
        .load(url)
        .placeholder(ContextCompat.getDrawable(context, R.drawable.bg_curved_corners_rose)?.apply {
            setTint(ContextCompat.getColor(context, R.color.grayLight))
        })
        .error(R.drawable.placeholder)
        .apply(RequestOptions().transforms(CenterCrop(), RoundedCorners(radius)))
        .into(this)
}

fun ImageView.loadWithRoundedCornerNoError(glide: Glide, url: String?, radius: Int) {
    glide.requestManagerRetriever.get(context)
        .load(url)
        .placeholder(ContextCompat.getDrawable(context, R.drawable.bg_curved_corners_rose)?.apply {
            setTint(ContextCompat.getColor(context, R.color.grayLight))
        }).error(ContextCompat.getDrawable(context, R.drawable.bg_curved_corners_rose)?.apply {
            setTint(ContextCompat.getColor(context, R.color.grayLight))
        })
        .apply(RequestOptions().transforms(CenterCrop(), RoundedCorners(radius)))
        .into(this)
}

fun ImageView.loadResWithRoundedCorner(glide: Glide, res: Int, radius: Int) {
    glide.requestManagerRetriever.get(context)
        .load(res)
        .placeholder(ContextCompat.getDrawable(context, R.drawable.bg_curved_corners_rose)?.apply {
            setTint(ContextCompat.getColor(context, R.color.grayLight))
        })
        .error(R.drawable.placeholder)
        .apply(RequestOptions().transforms(CenterCrop(), RoundedCorners(radius)))
        .into(this)
}

fun ImageView.loadPh(glide: Glide) {
    glide.requestManagerRetriever.get(context)
        .load(ContextCompat.getDrawable(context, R.drawable.bg_curved_corners_rose)?.apply {
            setTint(ContextCompat.getColor(context, R.color.grayLight))
        })
        .into(this)
}

fun ImageView.loadForAnim(glide: Glide, url: String?, phColor: Int, f: () -> Unit = {}) {
    val options = RequestOptions()
        .diskCacheStrategy(DiskCacheStrategy.DATA)
        .dontTransform()

    glide.requestManagerRetriever.get(context)
        .load(url)
        .placeholder(ColorDrawable(ContextCompat.getColor(context, phColor)))
        .error(R.drawable.placeholder)
        .apply(options)
        .listener(object : RequestListener<Drawable> {
            override fun onLoadFailed(
                e: GlideException?,
                model: Any?,
                target: Target<Drawable>?,
                isFirstResource: Boolean
            ): Boolean {
                f()
                return false
            }

            override fun onResourceReady(
                resource: Drawable?,
                model: Any?,
                target: Target<Drawable>?,
                dataSource: DataSource?,
                isFirstResource: Boolean
            ): Boolean {
                f()
                return false
            }

        })
        .into(this)
}

fun ImageView.loadRes(
    glide: Glide, @DrawableRes1 res: Int, isCircle: Boolean
) {
    glide.requestManagerRetriever.get(context)
        .load(res)
        .placeholder(ColorDrawable(ContextCompat.getColor(context, R.color.lightGrey)))
        .error(ColorDrawable(ContextCompat.getColor(context, R.color.lightGrey)))
        .apply { if (isCircle) apply(RequestOptions.circleCropTransform()) }
        .into(this)
}


fun Context.clearLocalData(preferenceStorage: PreferenceStorage, db: Database) {
    preferenceStorage.clearAll()

    db.clearAllTables()
}

fun Context.openInGMap(fromLat: String?, fromLong: String?, lat: String?, long: String?) {
    if (lat.isNullOrBlank() || long.isNullOrBlank()) return
    if (fromLat.isNullOrBlank() || fromLong.isNullOrBlank()) {
        val gmmIntentUri = Uri.parse("geo:$lat,$long")
        val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
        mapIntent.setPackage("com.google.android.apps.maps")
        if (mapIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(mapIntent)
        }
        return
    }
    val mapIntent = Intent(
        Intent.ACTION_VIEW,
        Uri.parse("http://maps.google.com/maps?saddr=$fromLat,$fromLong&daddr=$lat,$long")
    )
    mapIntent.setPackage("com.google.android.apps.maps")
    if (mapIntent.resolveActivity(getPackageManager()) != null) startActivity(mapIntent)

}

fun Context.openInWebview(url: String?) {
    if (url.isNullOrBlank()) return
    val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
    if (browserIntent.resolveActivity(getPackageManager()) != null) startActivity(browserIntent)

}

fun String.getAmMarkedTime(): String {
    if (isEmpty()) return ""
    val inputFormat = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
    val outputFormat = SimpleDateFormat("hh:mm a", Locale.getDefault())
    try {
        val date = inputFormat.parse(this)
        return outputFormat.format(date)
    } catch (e: ParseException) {

    }
    return ""

}

fun String?.convertToDDMMMY(): String {
    if (isNullOrEmpty()) return ""
    val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
    val outputFormat = SimpleDateFormat("dd MMM,yyyy", Locale.getDefault())
    try {
        val date = inputFormat.parse(this)
        return outputFormat.format(date)
    } catch (e: ParseException) {

    }

    return ""
}

fun String?.convertTo(df : String): String {
    if (isNullOrEmpty()) return ""
    val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
    val outputFormat = SimpleDateFormat(df, Locale.getDefault())
    try {
        val date = inputFormat.parse(this)
        return outputFormat.format(date)
    } catch (e: ParseException) {

    }

    return ""
}

fun String?.convertToDDMMMYWithOutT(): String {
    if (isNullOrEmpty()) return ""
    val inputFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
    val outputFormat = SimpleDateFormat("dd MMM, yyyy", Locale.getDefault())
    try {
        val date = inputFormat.parse(this)
        return outputFormat.format(date)
    } catch (e: ParseException) {

    }

    return ""
}

fun String?.convertToMMMddyyyy(): String {
    if (isNullOrEmpty()) return ""
    val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
    val outputFormat = SimpleDateFormat("MMM dd. yyyy", Locale.getDefault())
    try {
        val date = inputFormat.parse(this)
        return outputFormat.format(date)
    } catch (e: ParseException) {

    }

    return ""
}

fun String?.convertToMMMddyyyyMagento(): String {
    if (isNullOrEmpty()) return ""
    val inputFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
    inputFormat.timeZone = TimeZone.getTimeZone("UTC")
    val outputFormat = SimpleDateFormat("MMM dd. yyyy", Locale.getDefault())
    try {
        val date = inputFormat.parse(this)
        return outputFormat.format(date)
    } catch (e: ParseException) {

    }

    return ""
}

fun String?.convertToMMMddyyyyFrom(): String {
    if (isNullOrEmpty()) return ""
    val inputFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
    inputFormat.timeZone = TimeZone.getTimeZone("UTC")
    val outputFormat = SimpleDateFormat("dd MMM,yyyy '|' HH:mm a", Locale.getDefault())
    try {
        val date = inputFormat.parse(this)
        return outputFormat.format(date)
    } catch (e: ParseException) {

    }

    return ""
}

fun String?.convertToMMMddyyyy2From(): String {
    if (isNullOrEmpty()) return ""
    val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
    inputFormat.timeZone = TimeZone.getTimeZone("UTC")
    val outputFormat = SimpleDateFormat("dd MMM,yyyy '|' HH:mm a", Locale.getDefault())
    try {
        val date = inputFormat.parse(this)
        return outputFormat.format(date)
    } catch (e: ParseException) {

    }

    return ""
}

fun String.convertToDDMMMYNoComma(): String {
    if (isEmpty()) return ""
    val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
    val outputFormat = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
    try {
        val date = inputFormat.parse(this)
        return outputFormat.format(date)
    } catch (e: ParseException) {

    }

    return ""
}

fun String?.convertToCalendar(): Calendar {
    if (isNullOrBlank()) return Calendar.getInstance()
    val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
    val outputFormat = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
    try {
        val date = inputFormat.parse(this)
        return Calendar.getInstance().apply { time = date }
    } catch (e: ParseException) {

    }

    return Calendar.getInstance()
}

fun String?.getTimeFromTDate(): String {
    if (isNullOrEmpty()) return ""
    val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
    val outputFormat = SimpleDateFormat("hh:mm a", Locale.getDefault())
    try {
        val date = inputFormat.parse(this)
        return outputFormat.format(date)
    } catch (e: ParseException) {

    }

    return ""
}

fun Calendar.getFormatted(pattern: String):String{
    val sdf = SimpleDateFormat(pattern, Locale.getDefault())
    return sdf.format(time)
}


fun Calendar.dayEquals(cal: Calendar) = get(Calendar.YEAR) == cal.get(Calendar.YEAR) &&
        get(Calendar.DAY_OF_YEAR) == cal.get(Calendar.DAY_OF_YEAR)


fun Context.dial(phn: String) {
    val dialIntent = Intent(Intent.ACTION_DIAL).apply {
        setData(Uri.parse("tel:$phn"))
    }
    if (dialIntent.resolveActivity(getPackageManager()) != null) {
        startActivity(dialIntent)
    }
}


fun Context.safeColorParse(color: String): Int {
    try {
        return Color.parseColor(color)
    } catch (e: Exception) {
        return ContextCompat.getColor(this, R.color.colorPrimary)
    }
}

fun String?.extractYoutubeId(): String? {
    if (this == null) return null
    val pattern =
        "https?:\\/\\/(?:[0-9A-Z-]+\\.)?(?:youtu\\.be\\/|youtube\\.com\\S*[^\\w\\-\\s])([\\w\\-]{11})(?=[^\\w\\-]|$)(?![?=&+%\\w]*(?:['\"][^<>]*>|<\\/a>))[?=&+%\\w]*";

    val compiledPattern = Pattern.compile(
        pattern,
        Pattern.CASE_INSENSITIVE
    )
    val matcher = compiledPattern.matcher(this)
    if (matcher.find()) return matcher.group(1);
    return null;
}

fun Context.getCustomTabIntent(): CustomTabsIntent {
    val customTabsIntent = CustomTabsIntent.Builder()
        .addDefaultShareMenuItem()
        .setToolbarColor(ContextCompat.getColor(this, R.color.colorPrimary))
        .setShowTitle(true)
        .enableUrlBarHiding()
        .build()

    CustomTabsHelper.addKeepAliveExtra(this, customTabsIntent.intent)
    return customTabsIntent
}


fun String.getVimeoVideoId(): String {
    val pattern =
        Pattern.compile("https?://(?:www\\.|player\\.)?vimeo.com/(?:channels/(?:\\w+/)?|groups/([^/]*)/videos/|album/(\\d+)/video/|video/|)(\\d+)(?:$|/|\\?)");
    val matcher = pattern.matcher(this);
    if (matcher.find()) return matcher.group(3)
    else return ""
}

fun Spannable.stripUnderlines() {

    val spans = getSpans(0, length - 1, URLSpan::class.java)

    for (span in spans) {
        val start = getSpanStart(span)
        val end = getSpanEnd(span)
        removeSpan(span)
        val spanNew = URLSpanNoUnderline(span.url)
        setSpan(spanNew, start, end, 0)
    }


}


class URLSpanNoUnderline(url: String) : URLSpan(url) {
    override fun updateDrawState(ds: TextPaint) {
        super.updateDrawState(ds)
        ds.isUnderlineText = false
    }
}



