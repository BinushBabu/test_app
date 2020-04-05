package com.example.testbase.utils


import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.example.testbase.R
import com.example.testbase.data.*
import com.example.testbase.di.AppContext
import com.example.testbase.di.ComputationScheduler
import com.example.testbase.di.IoScheduler
import com.example.testbase.di.MainScheduler

import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton




@Singleton
data class AppRxSchedulers @Inject constructor(
    @IoScheduler val io: Scheduler,
    @ComputationScheduler val computation: Scheduler,
    @MainScheduler val main: Scheduler,
    @AppContext val context: Context
)

fun <T> Single<Response<ApiResponse<T>>>.schedule(scheduler: AppRxSchedulers, preferenceStorage: PreferenceStorage, db : Database): Single<T> {
    return subscribeOn(scheduler.io).map {
        if (it.isSuccessful()){
            if (it.body() == null )  throw ApiException(it.code(), it.message() ?: scheduler.context.getString(
                R.string.un_expected_error_occured))
            else return@map it.body()!!.data
        }else if (it.code() == 401) {
            scheduler.context.clearLocalData(preferenceStorage, db)
            throw ApiException(it.code(), it.message() ?: scheduler.context.getString(R.string.un_expected_error_occured))
        } else if(it.code() == 400){
            throw ApiException(it.code(), AppUtils.parseHttpError(it.errorBody()?.string())  ?: scheduler.context.getString(R.string.un_expected_error_occured))
        } else throw ApiException(it.code(),  it.message() ?: scheduler.context.getString(R.string.un_expected_error_occured))

    }
}

fun <T> Single<ArrayList<T>>.schedule(scheduler: AppRxSchedulers): Single<ArrayList<T>> {
    return subscribeOn(scheduler.io).map {return@map it}
}


fun <T> Single<Response<T>>.scheduleUnwrappedResponse(scheduler : AppRxSchedulers,preferenceStorage: PreferenceStorage,db : Database): Single<T> {
    return subscribeOn(scheduler.io).map {
        if (it.isSuccessful()){
            if (it.body() == null )  throw ApiException(it.code(), it.message() ?: scheduler.context.getString(R.string.un_expected_error_occured))
            else return@map it.body()!!
        } else if (it.code() == 401) {
            scheduler.context.clearLocalData(preferenceStorage, db)
            throw ApiException(it.code(), it.message() ?: scheduler.context.getString(R.string.un_expected_error_occured))
        } else if(it.code() == 400){
            throw ApiException(it.code(), AppUtils.parseHttpError(it.errorBody()?.string())  ?: scheduler.context.getString(R.string.un_expected_error_occured))
        } else throw ApiException(it.code(),  it.message() ?: scheduler.context.getString(R.string.un_expected_error_occured))

    }
}


fun <T> Single<Response<ApiResponse<T>>>.scheduleOnNewThread(scheduler: AppRxSchedulers,preferenceStorage: PreferenceStorage,db : Database): Single<T> {
    return subscribeOn(Schedulers.newThread()).map {
        if (it.isSuccessful()){
            if (it.body() == null )  throw ApiException(it.code(), it.message() ?: scheduler.context.getString(R.string.un_expected_error_occured))
            else return@map it.body()!!.data
        }else if (it.code() == 401) {
            scheduler.context.clearLocalData(preferenceStorage, db)
            throw ApiException(it.code(), it.message() ?: scheduler.context.getString(R.string.un_expected_error_occured))
        }
        else if(it.code() == 400){
            throw ApiException(it.code(), AppUtils.parseHttpError(it.errorBody()?.string())  ?: scheduler.context.getString(R.string.un_expected_error_occured))
        } else throw ApiException(it.code(),  it.message() ?: scheduler.context.getString(R.string.un_expected_error_occured))

    }
}

fun <T> Single<T>.subscribeResponse(compositeDisposable: CompositeDisposable,
                                    liveData:MutableLiveData<ViewState<T>>? = null) {
    compositeDisposable.add(doOnSubscribe {

        liveData?.postValue(ViewState.Loading())
    }.subscribe({
        liveData?.postValue(ViewState.Success(it))
    },{
        liveData?.postValue(ViewState.Error(it))
    }))
}





