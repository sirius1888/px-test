package com.example.px_test.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.px_test.domain.AuthUseCase
import com.vk.id.VKIDAuthFail
import com.vk.id.VKIDUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authUseCase: AuthUseCase
) : ViewModel() {

    private val _authState = MutableStateFlow<AuthState>(AuthState.Idle)
    val authState: StateFlow<AuthState> = _authState

    fun sendAuthData(token: String, userData: VKIDUser) {
        viewModelScope.launch {
            authUseCase(token, userData)
                .onStart { _authState.value = AuthState.Loading }
                .catch { exception -> _authState.value = AuthState.Error(exception.message ?: "Unknown error") }
                .collect { result ->
                    _authState.value = if (result.isSuccess) {
                        AuthState.Success
                    } else {
                        AuthState.Error(result.exceptionOrNull()?.message ?: "Unknown error")
                    }
                }
        }
    }

    fun notifyError(error: VKIDAuthFail) {
        _authState.value = AuthState.Error(error.description)
    }
}

sealed class AuthState {
    object Idle : AuthState()
    object Loading : AuthState()
    object Success : AuthState()
    data class Error(val message: String) : AuthState()
}