package com.same.alarm.login

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.user.UserApiClient
import com.same.alarm.domain.usecase.LoginUseCase
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
    private val _loginState = MutableSharedFlow<LoginState>(replay = 0)
    val loginState = _loginState.asSharedFlow()

    fun loginWithKakao() {
        UserApiClient.instance.loginWithKakaoAccount(context) { token, error ->
            viewModelScope.launch {
                when {
                    error != null -> _loginState.emit(LoginState.Failure(error))
                    token != null -> handleLoginSuccess(token)
                }
            }
        }
    }

    private suspend fun handleLoginSuccess(token: OAuthToken) {
        loginUseCase(token.accessToken)
            .onSuccess {
                _loginState.emit(LoginState.Success)
            }
            .onFailure { error ->
                _loginState.emit(LoginState.Failure(error))
            }
    }
}

sealed class LoginState {
    data object Success : LoginState()
    data class Failure(val error: Throwable) : LoginState()
}