package com.same.alarm.login.component

fun calculateAge(year: Int, month: Int, day: Int): Int {
        val today = java.time.LocalDate.now()
        val birthDate = try {
            java.time.LocalDate.of(year, month, day)
        } catch (e: Exception) {
            return 0
        }
        var age = today.year - birthDate.year
        if (today < birthDate.plusYears(age.toLong())) {
            age--
        }
        return age
    }