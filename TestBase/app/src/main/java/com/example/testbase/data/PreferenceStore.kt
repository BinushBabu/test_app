package com.example.testbase.data

import android.content.Context
import android.content.SharedPreferences
import androidx.annotation.WorkerThread
import com.example.testbase.di.AppContext
import javax.inject.Inject
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

interface PreferenceStorage {
    var loginStatus: Boolean
    var userId: String
    var authToken: String
    var userName: String

    fun clearAll()
}

class SharedPreferenceStorage(context: Context) :
    PreferenceStorage {
    private val prefs =
        context.applicationContext.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    override var loginStatus by BooleanPreference(prefs, PREF_LOGIN_STATUS, false)
    override var userId by StringPreference(prefs, PREF_USER_ID, "")
    override var authToken by StringPreference(prefs, PREF_AUTH_TOKEN, "")
    override var userName by StringPreference(prefs, PREF_USER_NAME, "")

    override fun clearAll() {
        prefs.edit().clear().apply()
    }

    companion object {
        const val PREFS_NAME = "es_pref"
        const val PREF_LOGIN_STATUS = "pref_login_status"
        const val PREF_USER_ID = "pref_user_id"
        const val PREF_AUTH_TOKEN = "pref_auth_token"
        const val PREF_USER_NAME = "pref_user_name"
    }
}

class BooleanPreference(
    private val preferences: SharedPreferences,
    private val name: String,
    private val defaultValue: Boolean
) : ReadWriteProperty<Any, Boolean> {

    @WorkerThread
    override fun getValue(thisRef: Any, property: KProperty<*>): Boolean {
        return preferences.getBoolean(name, defaultValue)
    }

    override fun setValue(thisRef: Any, property: KProperty<*>, value: Boolean) {
        preferences.edit().putBoolean(name, value).apply()
    }
}

class IntPreference(
    private val preferences: SharedPreferences,
    private val name: String,
    private val defaultValue: Int
) : ReadWriteProperty<Any, Int> {

    @WorkerThread
    override fun getValue(thisRef: Any, property: KProperty<*>): Int {
        return preferences.getInt(name, defaultValue)
    }

    override fun setValue(thisRef: Any, property: KProperty<*>, value: Int) {
        preferences.edit().putInt(name, value).apply()
    }
}

class StringPreference(
    private val preferences: SharedPreferences,
    private val name: String,
    private val defaultValue: String
) : ReadWriteProperty<Any, String> {

    @WorkerThread
    override fun getValue(thisRef: Any, property: KProperty<*>): String {
        return preferences.getString(name, defaultValue) ?: ""
    }

    override fun setValue(thisRef: Any, property: KProperty<*>, value: String) {
        preferences.edit().putString(name, value).apply()
    }
}

interface UserIndependentPref {
    var fcmToken: String

}

class UserIndependantPreferenceStore @Inject constructor(@AppContext context: Context) :
    UserIndependentPref {

    private val prefs =
        context.applicationContext.getSharedPreferences(USER_INDEPENDENT_PREF, Context.MODE_PRIVATE)

    override var fcmToken by StringPreference(prefs, FCM_TOKEN, "")


    companion object {
        const val USER_INDEPENDENT_PREF = "user_independant_pref"
        const val FCM_TOKEN = "fcm_token"
    }

}