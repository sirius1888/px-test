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
    }
}

