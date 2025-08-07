package com.same.alarm.model.alarm

import androidx.compose.ui.graphics.Color

enum class Category(val displayName: String, val color: Color) {
    WORK("일", Color(0xFF19C5D2)),
    HOBBY("취미", Color(0xFF1FB373)),
    HOSPITAL("병원", Color(0xFF637DAD)),
    DAILY("일상", Color(0xFFFFEA00)),
    STUDY("공부", Color(0xFFBB83FF)),
    ETC("기타", Color(0xFFFF79AA));

    companion object {
        fun fromDisplayName(name: String): Category? =
            entries.find { it.displayName == name }
    }
}