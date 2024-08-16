package com.example.px_test.domain

import com.example.px_test.data.AuthRepository
import com.vk.id.VKIDUser
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AuthUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    operator fun invoke(token: String, userData: VKIDUser): Flow<Result<Unit>> {
        return repository.sendAuthData(token, userData)
    }
}