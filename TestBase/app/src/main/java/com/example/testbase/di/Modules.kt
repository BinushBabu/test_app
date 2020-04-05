package com.example.testbase.di

import android.app.Application
import android.app.NotificationManager
import android.content.Context
import androidx.room.Room
import com.bumptech.glide.Glide
import com.example.testbase.BuildConfig
import com.example.testbase.data.Database
import com.example.testbase.data.PreferenceStorage
import com.example.testbase.data.SharedPreferenceStorage
import com.example.testbase.network.NetworkService
import com.example.testbase.network.HeaderInterceptor
import com.example.testbase.utils.App
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.tinder.scarlet.Scarlet
import com.tinder.scarlet.messageadapter.gson.GsonMessageAdapter
import com.tinder.scarlet.streamadapter.rxjava2.RxJava2StreamAdapterFactory
import com.tinder.scarlet.websocket.okhttp.newWebSocketFactory
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.Reusable
import dagger.android.AndroidInjectionModule
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module(includes = arrayOf(EsModule::class))
object AppModule {

    @Provides
    @Singleton
    @AppContext
    @JvmStatic
    fun provideContext(application: Application): Context {
        return application
    }


    @Provides
    @Singleton
    @JvmStatic
    fun provideEsService(retrofit: Retrofit) = retrofit.create(NetworkService::class.java)

    @Provides
    @Reusable
    @JvmStatic
    fun retrofit(
        okHttpClient: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory,
        callAdapterFactory: RxJava2CallAdapterFactory
    ): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(gsonConverterFactory)
            .addCallAdapterFactory(callAdapterFactory)
            .build()
    }

    @Provides
    @Singleton
    @JvmStatic
    fun gsonConverterFactory(gson: Gson): GsonConverterFactory {
        return GsonConverterFactory.create(gson)
    }

    @Provides
    @Singleton
    @JvmStatic
    fun callAdapterFactory(): RxJava2CallAdapterFactory {
        return RxJava2CallAdapterFactory.create()
    }

    @Provides
    @Singleton
    @JvmStatic
    fun gson(): Gson {
        val gsonBuilder = GsonBuilder()
        return gsonBuilder.create()
    }

    @Provides
    @Singleton
    @JvmStatic
    fun okHttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor,
        headerInterceptor: HeaderInterceptor
    ): OkHttpClient {
        return OkHttpClient()
            .newBuilder().apply {
                addInterceptor(headerInterceptor)
                if (BuildConfig.DEBUG) addInterceptor(httpLoggingInterceptor)
                connectTimeout(0, TimeUnit.SECONDS)
                readTimeout(0, TimeUnit.SECONDS)
                writeTimeout(0, TimeUnit.SECONDS)
            }
            .build()
    }

    @Provides
    @Reusable
    @JvmStatic
    fun httpLoggingInterceptor(): HttpLoggingInterceptor {
        val logging = HttpLoggingInterceptor()
        logging.level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
        return logging
    }


    @Provides
    @Singleton
    @JvmStatic
    fun getSharedPreferenceHelper(@AppContext context: Context): PreferenceStorage =
        SharedPreferenceStorage(context)

    @Provides
    @Reusable
    @JvmStatic
    fun glide(@AppContext context: Context): Glide {
        return Glide.get(context)
    }

    @Singleton
    @Provides
    @IoScheduler
    @JvmStatic
    fun provideIoScheduler(): Scheduler =  Schedulers.io()

    @Singleton
    @Provides
    @ComputationScheduler
    @JvmStatic
    fun provideComputationScheduler(): Scheduler =  Schedulers.computation()

    @Singleton
    @Provides
    @MainScheduler
    @JvmStatic
    fun provideAndroidScheduler(): Scheduler =  AndroidSchedulers.mainThread()

    @Provides
    @Singleton
    @JvmStatic
    fun getRoomDataBase(application: Application): Database =
        Room.databaseBuilder(
            application,
            Database::class.java, "esseplore_db"
        ).build()




   @Singleton
    @Provides
    @JvmStatic
    fun provideScarletForTrip(okHttpClient: OkHttpClient,preferenceStorage: PreferenceStorage): Scarlet {
        return Scarlet.Builder()
           .webSocketFactory(okHttpClient.newWebSocketFactory("${BuildConfig.BASE_URL_WS}?token=${preferenceStorage.authToken}"))
            .addMessageAdapterFactory(GsonMessageAdapter.Factory())
            .addStreamAdapterFactory(RxJava2StreamAdapterFactory())
            .build()
    }

    @Singleton
    @Provides
    @JvmStatic
    fun provideNotificationManager(@AppContext context: Context) : NotificationManager {
        return context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    }

}

@Module(includes = arrayOf(AndroidInjectionModule::class))
abstract class EsModule {

    @Binds
    abstract fun application(app: App) : Application
}





