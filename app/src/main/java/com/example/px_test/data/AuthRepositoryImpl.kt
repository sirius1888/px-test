package com.example.px_test.data

import com.example.px_test.data.network.ApiService
import com.example.px_test.data.network.AuthRequest
import com.vk.id.VKIDUser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

interface AuthRepository {
    fun sendAuthData(token: String, userData: VKIDUser): Flow<Result<Unit>>
}
class AuthRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : AuthRepository {
    override fun sendAuthData(token: String, userData: VKIDUser): Flow<Result<Unit>> = flow {
        try {
            val request = AuthRequest(
                token = token,
                userData = userData
            )
            val response = apiService.sendAuthData(request)

            if (response.isSuccessful) {
                emit(Result.success(Unit))
            } else {
                emit(Result.failure(Exception("Error: ${response.code()}")))
            }
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }.flowOn(Dispatchers.IO)
}
