package com.same.alarm.login.component

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.rememberAsyncImagePainter
import com.same.alarm.designsystem.R
import com.same.alarm.designsystem.noRippleClickable
import com.same.alarm.model.userinfo.Gender

@Composable
fun ProfileInputStep(
    userName: String,
    onNameChange: (String) -> Unit,
    selectedGender: Gender?,
    onGenderSelected: (Gender) -> Unit,
    selectedImageUri: Uri?,
    onImageSelected: (Uri) -> Unit,
    onNext: () -> Unit
) {
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let { onImageSelected(it) }
    }

    Column(modifier = Modifier
        .padding(top = 39.dp)
        .fillMaxSize()
    ) {
        Text(
            text = "프로필을 입력해 주세요.",
            style = TextStyle(
                fontFamily = FontFamily(Font(R.font.inter)),
                fontWeight = FontWeight.Bold,
                fontSize = 23.sp,
                lineHeight = 34.sp,
                letterSpacing = (-0.51).sp,
                color = Color.White
            ),
            modifier = Modifier.padding(start = 29.dp)
        )

        Spacer(Modifier.height(68.dp))

        Box(
            modifier = Modifier
                .size(164.dp)
                .align(Alignment.CenterHorizontally)
                .noRippleClickable { launcher.launch("image/*") }
                .clip(CircleShape)
                .background(Color(0xFFD9D9D9)),
            contentAlignment = Alignment.Center
        ) {
            if (selectedImageUri != null) {
                Image(
                    painter = rememberAsyncImagePainter(selectedImageUri),
                    contentDescription = "Selected Profile Image",
                    modifier = Modifier
                        .fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }
        }

        Spacer(Modifier.height(57.dp))

        NameInputField(
            userName = userName,
            onNameChange = onNameChange
        )

        Spacer(modifier = Modifier.height(21.dp))

        Row {
            GenderOption(
                text = "남성",
                onClick = { onGenderSelected(Gender.MALE) },
                isSelected = selectedGender == Gender.MALE,
                modifier = Modifier.weight(1f).padding(start = 21.dp)
            )

            Spacer(Modifier.width(18.dp))

            GenderOption(
                text = "여성",
                onClick = { onGenderSelected(Gender.FEMALE) },
                isSelected = selectedGender == Gender.FEMALE,
                modifier = Modifier.weight(1f).padding(end = 21.dp)
            )
        }
    }
}

@Composable
fun NameInputField(
    userName: String,
    onNameChange: (String) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 21.dp)
            .height(40.dp)
            .clip(RoundedCornerShape(50.dp))
            .background(Color(0xFFFFFFFF).copy(alpha = 0.3f)),
        contentAlignment = Alignment.CenterStart
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 18.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "이름",
                color = Color.White,
                fontFamily = FontFamily(Font(R.font.inter)),
                fontSize = 13.sp,
                fontWeight = FontWeight.Medium,
                letterSpacing = (-0.51).sp,
                lineHeight = 17.sp
            )

            Spacer(modifier = Modifier.width(20.dp))

            BasicTextField(
                value = userName,
                onValueChange = onNameChange,
                singleLine = true,
                textStyle = TextStyle(
                    color = Color.White,
                    fontSize = 16.sp,
                    fontFamily = FontFamily(Font(R.font.inter)),
                    fontWeight = FontWeight.Bold,
                    lineHeight = 17.sp,
                    letterSpacing = (-0.51).sp
                ),
                cursorBrush = SolidColor(Color.White),
                modifier = Modifier.weight(1f),
                decorationBox = { innerTextField ->
                    Box {
                        if (userName.isEmpty()) {
                            Text(
                                text = "이름을 입력하세요",
                                style = TextStyle(
                                    color = Color.White.copy(alpha = 0.5f),
                                    fontSize = 16.sp,
                                    fontFamily = FontFamily(Font(R.font.inter)),
                                    fontWeight = FontWeight.Normal
                                )
                            )
                        }
                        innerTextField()
                    }
                }
            )
        }
    }
}


@Composable
fun GenderOption(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    isSelected: Boolean = false
) {
    val backgroundColor = if (isSelected) Color(0xFFFF7542) else Color.White.copy(alpha = 0.3f)

    Box(
        modifier = modifier
            .height(40.dp)
            .clip(RoundedCornerShape(50.dp))
            .background(backgroundColor)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = Color.White,
            fontSize = 16.sp,
            fontFamily = FontFamily(Font(R.font.inter)),
            fontWeight = FontWeight.Bold,
            lineHeight = 17.sp,
            letterSpacing = (-0.51).sp
        )
    }
}
