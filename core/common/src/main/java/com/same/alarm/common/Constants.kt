package com.same.alarm.common

import java.time.DayOfWeek

object Constants {
    val DAYS_OF_WEEK = DayOfWeek.entries.toTypedArray()
    val CATEGORIES = listOf("스터디", "기상", "미팅", "집안일")
}