package com.example.testbase.network


import com.example.testbase.data.PreferenceStorage
import dagger.Reusable
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

@Reusable
class HeaderInterceptor @Inject constructor(private val pref: PreferenceStorage) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        request = request.newBuilder()
            .addHeader("Accept", "application/json")
            .addHeader("Authorization", if (pref.loginStatus) "JWT ${pref.authToken}" else "")
            .build()

        return chain.proceed(request)
    }
}