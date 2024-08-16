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

        setupUI()
        observeViewModel()
    }

    private fun setupUI() {
        binding.loginVk.setCallbacks(
            onAuth = { _, token ->
                viewModel.sendAuthData(token.token, token.userData)
            },
            onFail = { _, fail ->
                viewModel.notifyError(fail)
            }
        )
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            viewModel.authState.collect { state ->
                when (state) {
                    is AuthState.Idle -> {
                        binding.textStatus.text = "Ожидаю авторизации"
                        binding.textAuthentication.text = ""
                    }

                    is AuthState.Loading -> {
                        binding.textStatus.text = "Отправка токена..."
                        binding.textAuthentication.text =
                            "Отправляем данные: ${viewModel.getAuthRequestData()}"
                    }

                    is AuthState.Success -> {
                        binding.textStatus.text = "Токен успешно отправлен"
                        binding.textAuthentication.text = "Данные успешно отправлены на сервер"
                    }

                    is AuthState.Error -> {
                        binding.textStatus.text = "Ошибка: ${state.message}"
                        binding.textAuthentication.text = "Ошибка при отправке данных"
                    }
                }
            }
        }
    }
}
