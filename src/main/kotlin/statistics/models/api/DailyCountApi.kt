package ru.mav26.statistics.models.api

import kotlinx.serialization.Serializable
import java.time.LocalDate

@Serializable
data class DailyCountApi(
    val date: String,
    val light: Int,
    val medium: Int,
    val hard: Int
)
