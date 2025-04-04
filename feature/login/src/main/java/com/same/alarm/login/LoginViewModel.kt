package com.same.alarm.login

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.user.UserApiClient
import com.same.alarm.domain.usecase.LoginUseCase
import com.same.alarm.model.LoginRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val loginUseCase: LoginUseCase
) : ViewModel() {
    private val _loginEvent = MutableSharedFlow<LoginState>(replay = 0)
    val loginEvent = _loginEvent.asSharedFlow()

    fun loginWithKakao() {
        UserApiClient.instance.loginWithKakaoAccount(context) { token, error ->
            viewModelScope.launch {
                when {
                    error != null -> _loginEvent.emit(LoginState.Failure(error.toString()))
                    token != null -> handleLoginSuccess(token)
                }
            }
        }
    }

    private suspend fun handleLoginSuccess(token: OAuthToken) {
        loginUseCase(token.accessToken, LoginRequest("KAKAO"))
            .onSuccess {
                _loginEvent.emit(LoginState.Success)

            }
            .onFailure { error ->
                _loginEvent.emit(LoginState.Failure(error.toString()))
            }
    }
}

sealed class LoginState {
    data object Success : LoginState()
    data class Failure(val error: String) : LoginState()
}