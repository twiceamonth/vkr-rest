package ru.mav26.bosses.models.api

import kotlinx.serialization.Serializable

@Serializable
data class BossResponse(
    val bossId: String,
    val criteriaType: String,
    val criteriaValue: Int
)
