package ru.mav26.bosses.models.api

import kotlinx.serialization.Serializable

@Serializable
data class ActiveBossResponse(
    val bossName: String,
    val criteriaType: String,
    val criteriaValue: Int,
    val maxHp: Int,
    val resistType: String,
    val resistValue: Int,
    val bonusType: String,
    val bonusValue: Int,
    val baseDamage: Int,
    val imagePath: String,
    val currentHp: Int
)
