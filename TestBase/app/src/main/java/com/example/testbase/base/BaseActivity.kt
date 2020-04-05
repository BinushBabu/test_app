package com.example.testbase.base

import android.content.Context
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide

import com.example.testbase.R
import com.example.testbase.data.PreferenceStorage
import com.example.testbase.utils.errorToast
import com.example.testbase.utils.log
import com.example.testbase.widget.LoadingDialog
import com.google.android.gms.common.api.ApiException
import dagger.android.support.DaggerAppCompatActivity
import java.net.*
import javax.inject.Inject

@Suppress("DEPRECATION")
abstract class BaseActivity<T : ViewModel> : DaggerAppCompatActivity() {

    private val baseViewModelFactory: BaseViewModelFactory<T> by lazy { BaseViewModelFactory { viewModel } }

    @Inject
    lateinit var viewModel: T

    @Inject
    lateinit var preferenceStorage: PreferenceStorage

    @Inject
    lateinit var glide: Glide



    protected val loadingDialog by lazy(LazyThreadSafetyMode.NONE) {
        LoadingDialog(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this, baseViewModelFactory).get(viewModel::class.java)


    }

    private fun getViewModel(): BaseViewModel? = viewModel as BaseViewModel


    fun handleError(e: Throwable) {

        when (e) {
            is UnknownHostException -> getString(R.string.no_interenet).errorToast(this)
            is SocketTimeoutException -> getString(R.string.no_interenet).errorToast(this)
            is ConnectException -> getString(R.string.no_interenet).errorToast(this)
            is NoRouteToHostException -> getString(R.string.no_interenet).errorToast(this)
            is SocketException -> getString(R.string.no_interenet).errorToast(this)
            is ApiException -> {
                e.message?.errorToast(this)
            }
            else -> {
                e.message?.log()
                getString(R.string.un_expected_error_occured).errorToast(this)
            }
        }

    }

    fun showKeyboard() {
        try {
            val view = this.currentFocus
            val inputMethodManager =
                this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.toggleSoftInputFromWindow(
                view!!.windowToken,
                InputMethodManager.SHOW_FORCED,
                0
            )
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun hideActionBar(){
        supportActionBar?.hide();
    }

}
