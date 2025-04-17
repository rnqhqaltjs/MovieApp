package com.same.alarm.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
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
        onSaveUser = userInfoInputViewModel::saveUserInfo
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserInfoInputScreen(
    userName: String,
    onSaveUser: (UserInfoRequest) -> Unit
) {
    var name by remember { mutableStateOf(userName) }
    var age by remember { mutableIntStateOf(0) }
    var gender by remember { mutableStateOf(Gender.MALE) }
    var job by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }

    val genderOptions = listOf(Gender.MALE, Gender.FEMALE)
    var expanded by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("이름/별명") },
            modifier = Modifier.fillMaxWidth()
        )

        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded }
        ) {
            OutlinedTextField(
                value = gender.name,
                onValueChange = {},
                readOnly = true,
                label = { Text("성별") },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                modifier = Modifier.menuAnchor(MenuAnchorType.PrimaryNotEditable).fillMaxWidth()
            )

            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                genderOptions.forEach { option ->
                    DropdownMenuItem(
                        text = { Text(option.name) },
                        onClick = {
                            gender = option
                            expanded = false
                        }
                    )
                }
            }
        }

        OutlinedTextField(
            value = age.toString(),
            onValueChange = { age = it.toInt() },
            label = { Text("나이") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = job,
            onValueChange = { job = it },
            label = { Text("직업") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = address,
            onValueChange = { address = it },
            label = { Text("거주지") },
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = { onSaveUser(
                UserInfoRequest(name, age, gender, job, address)
            ) },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
        ) {
            Text("시작하기")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun UserInfoInputScreenPreview() {
    UserInfoInputScreen(
        userName = "",
        onSaveUser = {}
    )
}