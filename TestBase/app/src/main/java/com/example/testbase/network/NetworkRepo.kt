package com.example.testbase.network


import dagger.Reusable
import javax.inject.Inject


data class SignupRequest(
    val phone: String, val email: String, val countryCode: String,
    val firstName: String,
    val lastName: String,
    val dob: String,
    val gender: String,
    val password: String,
    val flp:String? = null
)
@Reusable
class NetworkRepo @Inject constructor(
    private val networkService: NetworkService
) {

    fun register(signupRequest: SignupRequest) = networkService.register(
        signupRequest.firstName,
        signupRequest.lastName,
        signupRequest.email,
        signupRequest.phone,
        signupRequest.countryCode,
        signupRequest.gender,
        signupRequest.dob,
        signupRequest.password,
        signupRequest.flp
    )


}