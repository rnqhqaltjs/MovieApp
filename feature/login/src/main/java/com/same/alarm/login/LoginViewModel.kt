package com.same.alarm.login

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.user.UserApiClient
import com.same.alarm.domain.usecase.auth.LoginUseCase
import com.same.alarm.domain.usecase.auth.TryAutoLoginUseCase
import com.same.alarm.login.model.LoginState
import com.same.alarm.model.login.AuthType
import com.same.alarm.model.login.LoginRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onSubscription
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val loginUseCase: LoginUseCase,
    private val tryAutoLoginUseCase: TryAutoLoginUseCase
) : ViewModel() {
    private val _loginEvent = MutableSharedFlow<LoginState>(replay = 0)
    val loginEvent = _loginEvent
        .onSubscription {
            tryAutoLogin()
        }.shareIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000)
        )

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
        loginUseCase(token.accessToken, LoginRequest(AuthType.KAKAO))
            .onSuccess { isNewUser ->
                _loginEvent.emit(LoginState.Success(isNewUser))
            }
            .onFailure { error ->
                _loginEvent.emit(LoginState.Failure(error.toString()))
            }
    }

    private fun tryAutoLogin() {
        viewModelScope.launch {
            tryAutoLoginUseCase()
                .onSuccess { isNewUser ->
                    _loginEvent.emit(LoginState.Success(isNewUser))
                }
        }
    }
}

