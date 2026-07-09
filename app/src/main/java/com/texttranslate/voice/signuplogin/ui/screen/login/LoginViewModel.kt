package com.texttranslate.voice.signuplogin.ui.screen.login

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.texttranslate.voice.R
import com.texttranslate.voice.signuplogin.domain.usecase.LoginUseCases
import com.texttranslate.voice.signuplogin.ui.util.PasswordErrorParser
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val loginUseCases: LoginUseCases
) : ViewModel() {
    var state by mutableStateOf(LoginState())
        private set

    fun onEvent(event: LoginEvent) {
        when(event) {
            is LoginEvent.EmailChange -> {
                state = state.copy(email = event.email)
            }
            is LoginEvent.PasswordChange -> {
                state = state.copy(password = event.password)
            }
            is LoginEvent.Login -> {
                login()
            }
        }
    }

    private fun login() {
        state = state.copy(
            emailError = null,
            passwordError = null
        )
        if (!loginUseCases.validateEmailUseCase(state.email)) {
            state = state.copy(
                emailError = context.getString(R.string.invalid_email)
            )
        }
        val passwordResult = loginUseCases.validatePasswordUseCase(state.password)
        state = state.copy(
            passwordError = PasswordErrorParser.parseError(passwordResult)
        )

        if (state.emailError == null && state.passwordError == null) {
            state = state.copy(isLoading = true)
            viewModelScope.launch(Dispatchers.IO) {
                loginUseCases.loginWithEmailUseCase(state.email, state.password).onSuccess {
                    state = state.copy(isLoggedIn = true, isLoading = false)
                }.onFailure {
                    state = state.copy(emailError = it.message, isLoading = false)
                }
            }
        }
    }
}