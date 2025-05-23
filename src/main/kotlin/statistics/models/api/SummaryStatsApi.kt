package ru.mav26.statistics.models.api

import kotlinx.serialization.Serializable

@Serializable
data class SummaryStatsApi(
    val total: Int,
    val light: Int,
    val medium: Int,
    val hard: Int
)
