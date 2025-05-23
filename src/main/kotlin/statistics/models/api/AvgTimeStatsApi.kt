package ru.mav26.statistics.models.api

import kotlinx.serialization.Serializable

@Serializable
data class AvgTimeStatsApi(
    val lightDays: Double,
    val mediumDays: Double,
    val hardDays: Double
)
