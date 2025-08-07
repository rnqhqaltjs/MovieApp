package com.same.alarm.login

import android.net.Uri
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.same.alarm.designsystem.R
import com.same.alarm.login.component.BirthInputStep
import com.same.alarm.login.component.ConclusionStep
import com.same.alarm.login.component.IntroductionStep
import com.same.alarm.login.component.JobInputStep
import com.same.alarm.login.component.LocationInputStep
import com.same.alarm.login.component.ProfileInputStep
import com.same.alarm.login.component.SNSInputStep
import com.same.alarm.login.model.UserInfoInputState
import com.same.alarm.model.userinfo.Gender
import com.same.alarm.model.userinfo.UserInfoRequest

@Composable
fun UserInfoInputRoute(
    onUserInfoInputSuccess: () -> Unit,
    onShowSnackBar: (String) -> Unit,
    userInfoInputViewModel: UserInfoInputViewModel = hiltViewModel()
) {
    val userName by userInfoInputViewModel.userName.collectAsStateWithLifecycle()
    val selectedJob by userInfoInputViewModel.selectedJob.collectAsStateWithLifecycle()
    val age by userInfoInputViewModel.age.collectAsStateWithLifecycle()
    val gender by userInfoInputViewModel.gender.collectAsStateWithLifecycle()
    val location by userInfoInputViewModel.location.collectAsStateWithLifecycle()
    val snsId by userInfoInputViewModel.snsId.collectAsStateWithLifecycle()
    val showWebView by userInfoInputViewModel.showWebView.collectAsStateWithLifecycle()
    val selectedImageUri by userInfoInputViewModel.selectedImageUri.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        userInfoInputViewModel.userInfoInputEvent.collect {
            when (it) {
                is UserInfoInputState.Success -> onUserInfoInputSuccess()
                is UserInfoInputState.Failure -> onShowSnackBar(it.error)
            }
        }
    }

    UserInfoInputScreen(
        userName = userName,
        updateUserName = userInfoInputViewModel::updateUserName,
        selectedJob = selectedJob,
        toggleJob = userInfoInputViewModel::toggleJob,
        age = age,
        onAgeChanged = userInfoInputViewModel::updateAge,
        gender = gender,
        onGenderSelected = userInfoInputViewModel::updateGender,
        location = location,
        onLocationChange = userInfoInputViewModel::updateLocation,
        snsId = snsId,
        onSnsIdChange = userInfoInputViewModel::updateSnsId,
        showWebView = showWebView,
        toggleWebView = userInfoInputViewModel::toggleWebView,
        onSaveUser = userInfoInputViewModel::saveUserInfo,
        selectedImageUri = selectedImageUri,
        onImageSelected = userInfoInputViewModel::setSelectedImageUri,
    )
}

@Composable
fun UserInfoInputScreen(
    userName: String,
    updateUserName: (String) -> Unit,
    selectedJob: String?,
    toggleJob: (String) -> Unit,
    age: Int,
    onAgeChanged: (Int) -> Unit,
    gender: Gender?,
    onGenderSelected: (Gender) -> Unit,
    location: String,
    onLocationChange: (String) -> Unit,
    snsId: String,
    onSnsIdChange: (String) -> Unit,
    showWebView: Boolean,
    toggleWebView: (Boolean) -> Unit,
    onSaveUser: (UserInfoRequest, Uri?) -> Unit,
    selectedImageUri: Uri?,
    onImageSelected: (Uri) -> Unit
) {
    var step by rememberSaveable { mutableIntStateOf(1) }
    val totalSteps = 7
    val pagerState = rememberPagerState(
        initialPage = step - 1,
        initialPageOffsetFraction = 0f,
        pageCount = { totalSteps }
    )

    BackHandler(enabled = step > 1 || showWebView) {
        if (showWebView) {
            toggleWebView(false)
        } else {
            step--
        }
    }

    LaunchedEffect(step) {
        pagerState.animateScrollToPage(step - 1)
    }

    val isNextEnabled = when (step) {
        2 -> userName.isNotBlank() && gender != null && selectedImageUri != null
        4 -> !selectedJob.isNullOrBlank()
        5 -> location.isNotBlank()
        6 -> snsId.isNotBlank()
        else -> true
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(255, 106, 51, 204),
                        Color(255, 211, 115, 204)
                    )
                )
            )
            .padding(bottom = if (showWebView) 0.dp else 27.dp)
    ) {
        if (showWebView) {
            InstagramWebViewScreen(
                username = snsId,
                onBackClick = { toggleWebView(false) }
            )
            return
        }

        Row(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 24.dp),
            horizontalArrangement = Arrangement.spacedBy(17.dp)
        ) {
            repeat(totalSteps) { index ->
                Box(
                    modifier = Modifier
                        .size(10.dp)
                        .clip(CircleShape)
                        .background(
                            if (index < step) Color(0xFFFF7542) else Color.White
                        )
                )
            }
        }

        HorizontalPager(
            state = pagerState,
            userScrollEnabled = false,
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 24.dp)
        ) { page ->
            when (page + 1) {
                1 -> IntroductionStep(onNext = { step++ })

                2 -> ProfileInputStep(
                    userName = userName,
                    onNameChange = updateUserName,
                    onNext = { step++ },
                    selectedGender = gender,
                    onGenderSelected = onGenderSelected,
                    selectedImageUri = selectedImageUri,
                    onImageSelected = onImageSelected
                )

                3 -> BirthInputStep(
                    onNext = { step++ },
                    onAgeChanged = onAgeChanged
                )

                4 -> JobInputStep(
                    onNext = { step++ },
                    selectedJob = selectedJob,
                    toggleJob = toggleJob
                )

                5 -> LocationInputStep(
                    onNext = { step++ },
                    location = location,
                    onLocationChange = onLocationChange,
                    onSearchClick = {}
                )

                6 ->  SNSInputStep(
                    onNext = { step++ },
                    id = snsId,
                    onIdChange = onSnsIdChange,
                    onWebViewClick = {
                        if (snsId.isNotBlank()) {
                            toggleWebView(true)
                        }
                    }
                )

                7 -> ConclusionStep(
                    onNext = { step++ }
                )
            }
        }

        Button(
            onClick = {
                if (step == totalSteps) {
                    onSaveUser(
                        UserInfoRequest(userName, age, gender!!, selectedJob!!, location), selectedImageUri
                    )
                } else {
                    step++
                }
            },
            enabled = isNextEnabled,
            modifier = Modifier
                .fillMaxWidth()
                .height(46.dp)
                .padding(horizontal = 17.dp)
                .align(Alignment.BottomCenter),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFFF7542),
                contentColor = Color.White,
                disabledContainerColor = Color(0x66FF7542),
                disabledContentColor = Color.White.copy(alpha = 0.6f)
            ),
        ) {
            Text(
                text = if (step == totalSteps) "시작하기" else "다음",
                textAlign = TextAlign.Center,
                style = TextStyle(
                    fontFamily = FontFamily(Font(R.font.inter)),
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    lineHeight = 17.sp,
                    letterSpacing = (-0.51).sp
                ),
            )
        }
    }
}

@Composable
fun InstagramWebViewScreen(username: String, onBackClick: () -> Unit) {
    val context = LocalContext.current
    val url = "https://www.instagram.com/$username/"

    AndroidView(
        factory = {
            WebView(context).apply {
                webViewClient = WebViewClient()
                settings.javaScriptEnabled = true
                loadUrl(url)
            }
        },
        modifier = Modifier.fillMaxSize()
    )
}

@Preview(showBackground = true)
@Composable
fun UserInfoInputScreenPreview() {
    UserInfoInputScreen(
        userName = "홍길동",
        updateUserName = {},
        onSaveUser = { userInfoRequest: UserInfoRequest, imageUri: Uri? ->
        },
        selectedJob = "프리랜서",
        toggleJob = {},
        age = 25,
        onAgeChanged = {},
        gender = Gender.MALE,
        onGenderSelected = {},
        location = "서울",
        onLocationChange = {},
        snsId = "gil_dong",
        onSnsIdChange = {},
        showWebView = false,
        toggleWebView = {},
        selectedImageUri = null,
        onImageSelected = {}
    )
}