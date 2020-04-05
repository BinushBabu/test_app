package com.example.testbase.utils

import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.annotation.TargetApi
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.IntentSender
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.gms.location.LocationSettingsStatusCodes
import com.google.android.gms.tasks.RuntimeExecutionException
import java.util.ArrayList


class PermissionHelper {

    companion object {

        const val REQUEST_LOCATION = 111
        const val REQUEST_OVERLAY = 112
        const val REQUEST_TURN_ON_LOC = 113


        @TargetApi(Build.VERSION_CODES.M)
        fun requestLocationAccess(activity: Activity): Boolean {

            if (isPermissionRequestNeeded()) {
                //Boolean to check whether "Never show this again" is checked in permission dialog
                var isAccessDenied: Boolean? = false
                val permissions = ArrayList<String>()
                permissions.add(ACCESS_FINE_LOCATION)


                if (isAccessDenied == false) {
                    if (permissions.size == 0)
                        return true
                    else {
                        val mStringArray = permissions.toTypedArray()
                        activity.requestPermissions(mStringArray, REQUEST_LOCATION)
                        return false
                    }
                } else {
                    return false
                }

            } else {
                return true
            }
        }

        private fun isPermissionRequestNeeded(): Boolean {
            return Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1
        }

        fun hasLocPermission(c: Context) : Boolean {
            return ContextCompat.checkSelfPermission(c,
                    ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
        }

        @TargetApi(Build.VERSION_CODES.M)
        fun requestLocationAccess(fragment: Fragment): Boolean {

            if (isPermissionRequestNeeded()) {
                if (fragment.activity == null) return false
                //Boolean to check whether "Never show this again" is checked in permission dialog
                var isAccessDenied: Boolean? = false
                val permissions = ArrayList<String>()
                permissions.add(ACCESS_FINE_LOCATION)


                if (isAccessDenied == false) {
                    if (permissions.size == 0)
                        return true
                    else {
                        val mStringArray = permissions.toTypedArray()
                        fragment.requestPermissions(mStringArray, REQUEST_LOCATION)
                        return false
                    }
                } else {
                    return false
                }

            } else {
                return true
            }
        }

        fun requestOverlayPermissions(activity: Activity) : Boolean {
            if (isPermissionRequestNeeded()) {

                var isAccessDenied = false
                if (!Settings.canDrawOverlays(activity)) {
                    isAccessDenied = true
                }

                if (isAccessDenied) {
                    activity.startActivityForResult(
                        Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                            Uri.parse("package:" + activity.packageName)), REQUEST_OVERLAY)
                    return false
                }
                else {
                    return true
                }
            } else return true
        }

        fun showEnableLocSett(frag: Fragment?, activity: Activity?, shownLocSett: Boolean, callBack: (Boolean) -> Unit) {
            //TODO: Remove this below for release
            callBack(true)
            return
            val builder = LocationSettingsRequest.Builder()
                .addLocationRequest(LocationRequest().setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY))
            builder.setAlwaysShow(true)
            val result = LocationServices.getSettingsClient(frag?.context ?: activity!!).checkLocationSettings(builder.build())

            result.addOnSuccessListener {
                callBack(true)
            }
            result.addOnFailureListener {
                    val resolvableApiEx = it as ResolvableApiException
                    when (resolvableApiEx.statusCode) {
                        LocationSettingsStatusCodes.RESOLUTION_REQUIRED -> {
                            try {
                                if (!shownLocSett) {
                                    if (frag?.isAdded == true) frag.startIntentSenderForResult(resolvableApiEx.resolution.
                                        intentSender, REQUEST_TURN_ON_LOC, null, 0,0,
                                        0, null)
                                    else activity?.startIntentSenderForResult(resolvableApiEx.resolution.
                                        intentSender, REQUEST_TURN_ON_LOC, null, 0,0,
                                        0, null)

                                }else callBack(true)
                            } catch (sendIntentExcep: IntentSender.SendIntentException) {
                                sendIntentExcep.localizedMessage.log()
                                callBack(false)
                            } catch (classCastExcept:ClassCastException) {
                                classCastExcept.localizedMessage.log()
                                callBack(false)
                            }
                        }
                    }

            }
        }
    }
}