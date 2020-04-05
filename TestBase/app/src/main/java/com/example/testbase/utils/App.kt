package  com.example.testbase.utils


import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import io.reactivex.plugins.RxJavaPlugins
import  com.example.testbase.di.DaggerAppComponent


class App : DaggerApplication() {


    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
           return  DaggerAppComponent.factory().create(this)
       }


    override fun onCreate() {
        super.onCreate()
        //Global Rxjava2 Error handler for handling undeleivered errors
        RxJavaPlugins.setErrorHandler {
            AppUtils.logD(AppUtils.RX_JAVA_PLUGIN_ERROR,it.toString())
        }

    }

}