package com.example.testbase.base

import android.os.Bundle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders

import com.example.testbase.R
import com.example.testbase.utils.errorToast
import com.example.testbase.utils.toast
import com.google.android.gms.common.api.ApiException

import dagger.android.support.DaggerDialogFragment
import java.net.*
import javax.inject.Inject

open class BaseDialogFragment <T : ViewModel> : DaggerDialogFragment() {
    private val baseViewModelFactory: BaseViewModelFactory<T> by lazy { BaseViewModelFactory { viewModel } }


    @Inject
    lateinit var viewModel: T

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this, baseViewModelFactory).get(viewModel::class.java)
    }


    fun handleError(e: Throwable){

        when (e) {


            is UnknownHostException -> getString(R.string.no_interenet).toast(requireContext())
            is SocketTimeoutException -> getString(R.string.no_interenet).toast(requireContext())
            is ConnectException -> getString(R.string.no_interenet).toast(requireContext())
            is NoRouteToHostException -> getString(R.string.no_interenet).toast(requireContext())
            is SocketException -> getString(R.string.no_interenet).toast(requireContext())
            is ApiException -> {
           e.message?.errorToast(requireContext())
            }
            else -> {
                getString(R.string.un_expected_error_occured).toast(requireContext())
            }
        }

    }

    private fun getViewModel(): BaseViewModel? = viewModel as BaseViewModel
}