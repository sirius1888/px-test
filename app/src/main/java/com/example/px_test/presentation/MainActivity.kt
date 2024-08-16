package com.example.px_test.presentation

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.px_test.common.viewBinding
import com.example.px_test.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val binding by viewBinding(ActivityMainBinding::inflate)
    private val viewModel: AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        loginAction()
        subscribeToEvent()
    }

    private fun loginAction() {
        binding.loginVk.setCallbacks(
            onAuth = { _, token ->
                viewModel.sendAuthData(token.token, token.userData)
            },
            onFail = { _, fail ->
                viewModel.notifyError(fail)
            }
        )
    }

    private fun subscribeToEvent() {
        lifecycleScope.launch {
            viewModel.authState.collect { state ->
                when (state) {
                    is AuthState.Idle -> binding.textStatus.text = "Ожидаю авторизации"
                    is AuthState.Loading -> binding.textStatus.text = "Отправка токена..."
                    is AuthState.Success -> binding.textStatus.text = "Токен успешно отправлен"
                    is AuthState.Error -> binding.textStatus.text = "Ошибка: ${state.message}"
                }
            }
        }
    }
}

