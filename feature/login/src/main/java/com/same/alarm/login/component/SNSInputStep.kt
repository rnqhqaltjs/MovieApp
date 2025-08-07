package com.same.alarm.login.component

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.same.alarm.designsystem.R

@Composable
fun SNSInputStep(
    onNext: () -> Unit,
    id: String,
    onIdChange: (String) -> Unit,
    onWebViewClick: () -> Unit
) {
    Column(modifier = Modifier
        .padding(top = 39.dp)
        .fillMaxSize()
    ) {
        Text(
            text = "SNS를 등록하면 더 많은 사람들이\n 나를 알게 돼요.",
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

        Spacer(Modifier.height(30.dp))

        Column(
            modifier = Modifier
                .height(136.dp)
                .fillMaxWidth()
                .padding(horizontal = 21.dp)
                .clip(RoundedCornerShape(50.dp))
                .background(Color.White.copy(alpha = 0.3f)),

        ) {
            Spacer(Modifier.height(13.dp))

            InstagramInputField(
                id = id,
                onIdChange = onIdChange,
                onWebViewClick = onWebViewClick
            )

            Spacer(Modifier.height(16.dp))

            Text(
                text = "자동 생성 링크 미리보기",
                style = TextStyle(
                    fontFamily = FontFamily(Font(R.font.inter)),
                    fontWeight = FontWeight.Bold,
                    fontSize = 13.sp,
                    lineHeight = 16.sp,
                    letterSpacing = (-0.51).sp,
                    color = Color.White
                ),
                modifier = Modifier.padding(start = 46.dp)
            )

            Spacer(Modifier.height(7.dp))

            Text(
                text = "https://instagram.com/${id}",
                style = TextStyle(
                    fontFamily = FontFamily(Font(R.font.inter)),
                    fontWeight = FontWeight.Medium,
                    fontSize = 13.sp,
                    lineHeight = 16.sp,
                    letterSpacing = (-0.51).sp,
                    color = Color.White
                ),
                modifier = Modifier.padding(start = 46.dp)
            )
        }
    }
}

@Composable
fun InstagramInputField(
    id: String,
    onIdChange: (String) -> Unit,
    onWebViewClick: () -> Unit
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
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 17.dp, end = 9.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = com.same.alarm.login.R.drawable.ic_search),
                contentDescription = "이름 아이콘",
                modifier = Modifier.size(21.dp)
            )

            Spacer(modifier = Modifier.width(13.dp))

            Box(
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 8.dp),
                contentAlignment = Alignment.CenterStart
            ) {
                BasicTextField(
                    value = id,
                    onValueChange = onIdChange,
                    singleLine = true,
                    maxLines = 1,
                    textStyle = TextStyle(
                        color = Color.White,
                        fontSize = 16.sp,
                        fontFamily = FontFamily(Font(R.font.inter)),
                        fontWeight = FontWeight.Bold,
                        lineHeight = 17.sp,
                        letterSpacing = (-0.51).sp
                    ),
                    cursorBrush = SolidColor(Color.White),
                    modifier = Modifier.fillMaxWidth(),
                    decorationBox = { innerTextField ->
                        Box(
                            modifier = Modifier.fillMaxWidth(),
                            contentAlignment = Alignment.CenterStart
                        ) {
                            if (id.isEmpty()) {
                                Text(
                                    text = "인스타그램 아이디 검색",
                                    style = TextStyle(
                                        color = Color.White.copy(alpha = 0.5f),
                                        fontSize = 13.sp,
                                        fontFamily = FontFamily(Font(R.font.inter)),
                                        fontWeight = FontWeight.Medium
                                    ),
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis,
                                )
                            }
                            innerTextField()
                        }
                    }
                )
            }

            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(20.dp))
                    .background(Color(0xFFFF7542))
                    .clickable { onWebViewClick() }
                    .width(63.dp)
                    .height(28.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "검색",
                    color = Color.White,
                    fontSize = 11.sp,
                    fontFamily = FontFamily(Font(R.font.inter)),
                    fontWeight = FontWeight.SemiBold,
                    letterSpacing = (-0.3).sp
                )
            }
        }
    }
}