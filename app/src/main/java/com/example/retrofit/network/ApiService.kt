package com.example.retrofit.network

import com.example.retrofit.models.User
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
interface ApiService {
    @GET("users/{id}")  // Fetch user details
    fun getUser(@Path("id") userId: Int): Call<User>

    @GET("users")  // Fetch all users
    fun getAllUsers(): Call<List<User>>
}