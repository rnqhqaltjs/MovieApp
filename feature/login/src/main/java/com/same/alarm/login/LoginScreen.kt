package com.same.alarm.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.same.alarm.login.model.LoginState

@Composable
fun LoginRoute(
    onLoginSuccess: (Boolean) -> Unit,
    onShowSnackBar: (String) -> Unit,
    loginViewModel: LoginViewModel = hiltViewModel()
) {
    LaunchedEffect(Unit) {
//        loginViewModel.loginEvent.collect {
//            when (it) {
//                is LoginState.Success -> onLoginSuccess(it.isNewUser)
//                is LoginState.Failure -> onShowSnackBar(it.error)
//            }
//        }


        onLoginSuccess(false)
    }

    LoginScreen(
        onLoginClick = loginViewModel::loginWithKakao
    )
}

@Composable
fun LoginScreen(
    onLoginClick: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            onClick = onLoginClick
        ) {
            Text("로그인")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    LoginScreen(
        onLoginClick = {}
    )
}