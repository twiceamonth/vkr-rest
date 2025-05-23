package ru.mav26.statistics.models.api

import kotlinx.serialization.Serializable

@Serializable
data class DailyStatsApi(
    val counts: List<DailyCountApi>
)
