package ru.mav26.statistics.models.dto

import java.time.LocalDate

data class DailyCount(
    val date: LocalDate,
    val light: Int,
    val medium: Int,
    val hard: Int
)
