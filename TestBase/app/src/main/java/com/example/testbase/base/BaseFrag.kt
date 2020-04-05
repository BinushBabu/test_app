package com.example.testbase.base

import android.os.Bundle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders

import com.example.testbase.R
import com.example.testbase.utils.errorToast
import com.example.testbase.utils.log
import com.example.testbase.widget.LoadingDialog
import com.google.android.gms.common.api.ApiException
import dagger.android.support.DaggerFragment
import java.net.*
import javax.inject.Inject

abstract class BaseFrag<T : ViewModel> : DaggerFragment() {
    private val baseViewModelFactory: BaseViewModelFactory<T> by lazy { BaseViewModelFactory { viewModel } }

    protected val loadingDialog by lazy(LazyThreadSafetyMode.NONE) {
        LoadingDialog(requireContext())
    }

    @Inject
    lateinit var viewModel: T

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this, baseViewModelFactory).get(viewModel::class.java)
    }

    fun handleError(e: Throwable){
        e.message?.log()
        when (e) {
            is UnknownHostException -> getString(R.string.no_interenet).errorToast(requireContext())
            is SocketTimeoutException -> getString(R.string.no_interenet).errorToast(requireContext())
            is ConnectException -> getString(R.string.no_interenet).errorToast(requireContext())
            is NoRouteToHostException -> getString(R.string.no_interenet).errorToast(requireContext())
            is SocketException -> getString(R.string.no_interenet).errorToast(requireContext())
            is ApiException -> {
                e.message?.errorToast(requireContext())
            }
            else -> {
                getString(R.string.un_expected_error_occured).errorToast(requireContext())
            }
        }

    }

    private fun getViewModel(): BaseViewModel? = viewModel as BaseViewModel
}