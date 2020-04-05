package com.example.testbase.network

import com.example.testbase.data.ApiResponse
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface NetworkService {


    @FormUrlEncoded
    @POST("/api/user/register-single-step")
    fun register(
        @Field("first_name") first_name: String,
        @Field("last_name") last_name: String,
        @Field("email") email: String,
        @Field("mobile") mobile: String,
        @Field("country_code") country_code: String,
        @Field("gender") gender: String,
        @Field("dob") dob: String,
        @Field("password") password: String,
        @Field("tag_ids") tag_ids: String?
    ): Single<Response<ApiResponse<Any>>>
}