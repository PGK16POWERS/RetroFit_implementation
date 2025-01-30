package com.example.retrofit.network

import com.example.retrofit.models.User
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("/some-endpoint") // Your server endpoint
    suspend fun sendDataToServer(@Body user: User): Response<Any>
}