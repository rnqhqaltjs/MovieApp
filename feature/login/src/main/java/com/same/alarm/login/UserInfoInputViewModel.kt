package com.same.alarm.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.same.alarm.domain.usecase.GetUserInfoUseCase
import com.same.alarm.domain.usecase.SaveUserInfoUseCase
import com.same.alarm.login.model.UserInfoInputState
import com.same.alarm.model.userinfo.UserInfoRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.onSubscription
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserInfoInputViewModel @Inject constructor(
    private val getUserInfoUseCase: GetUserInfoUseCase,
    private val saveUserInfoUseCase: SaveUserInfoUseCase
) : ViewModel() {
    private val _userInfoInputEvent = MutableSharedFlow<UserInfoInputState>(replay = 0)
    val userInfoInputEvent = _userInfoInputEvent.asSharedFlow()

    private val _userName = MutableStateFlow("")
    val userName: StateFlow<String> = _userName
        .onSubscription {
            getUserName()
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = "",
        )

    private fun getUserName() {
        viewModelScope.launch {
            _userName.value = getUserInfoUseCase().name
        }
    }

    fun saveUserInfo(userInfoRequest: UserInfoRequest) {
        viewModelScope.launch {
            saveUserInfoUseCase(userInfoRequest)
                .onSuccess {
                    _userInfoInputEvent.emit(UserInfoInputState.Success)
            }
                .onFailure { error ->
                    _userInfoInputEvent.emit(UserInfoInputState.Failure(error.toString()))
            }
        }
    }
}