package com.example.px_test.data.network

import com.vk.id.VKIDUser
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

data class AuthRequest(val token: String, val userData: VKIDUser)

interface ApiService {
    @POST("auth/vk")
    suspend fun sendAuthData(@Body request: AuthRequest): Response<Unit>
}
