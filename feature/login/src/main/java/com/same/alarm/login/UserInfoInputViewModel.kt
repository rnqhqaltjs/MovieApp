package com.same.alarm.login

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.same.alarm.domain.usecase.user.GetUserInfoUseCase
import com.same.alarm.domain.usecase.user.SaveUserInfoUseCase
import com.same.alarm.login.model.UserInfoInputState
import com.same.alarm.model.userinfo.Gender
import com.same.alarm.model.userinfo.UserInfoRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onSubscription
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class UserInfoInputViewModel @Inject constructor(
    private val saveUserInfoUseCase: SaveUserInfoUseCase
) : ViewModel() {
    private val _userInfoInputEvent = MutableSharedFlow<UserInfoInputState>(replay = 0)
    val userInfoInputEvent = _userInfoInputEvent.asSharedFlow()

    private val _userName = MutableStateFlow("")
    val userName: StateFlow<String> = _userName

    private val _selectedJob = MutableStateFlow<String?>(null)
    val selectedJob: StateFlow<String?> = _selectedJob.asStateFlow()

    private val _age = MutableStateFlow(0)
    val age: StateFlow<Int> = _age

    private val _gender = MutableStateFlow<Gender?>(null)
    val gender: StateFlow<Gender?> = _gender

    private val _location = MutableStateFlow("")
    val location: StateFlow<String> = _location

    private val _snsId = MutableStateFlow("")
    val snsId: StateFlow<String> = _snsId

    private val _showWebView = MutableStateFlow(false)
    val showWebView: StateFlow<Boolean> = _showWebView

    private val _selectedImageUri = MutableStateFlow<Uri?>(null)
    val selectedImageUri: StateFlow<Uri?> = _selectedImageUri.asStateFlow()

    fun toggleJob(job: String) {
        _selectedJob.value = if (_selectedJob.value == job) {
            null
        } else {
            job
        }
    }

    fun setSelectedImageUri(uri: Uri) {
        _selectedImageUri.value = uri
    }

    fun updateUserName(newName: String) {
        _userName.value = newName
    }

    fun updateAge(newAge: Int) {
        _age.value = newAge
    }

    fun updateGender(gender: Gender) {
        _gender.value = gender
    }

    fun updateLocation(newLocation: String) {
        _location.value = newLocation
    }

    fun updateSnsId(newId: String) {
        _snsId.value = newId
    }

    fun toggleWebView(show: Boolean) {
        _showWebView.value = show
    }

    fun saveUserInfo(userInfoRequest: UserInfoRequest, image: Uri?) {
        viewModelScope.launch {
            saveUserInfoUseCase(userInfoRequest, image)
                .onSuccess {
                    _userInfoInputEvent.emit(UserInfoInputState.Success)
                }
                .onFailure { error ->
                    error.message?.let {
                        _userInfoInputEvent.emit(UserInfoInputState.Failure(it))
                    }
                }
        }
    }
}