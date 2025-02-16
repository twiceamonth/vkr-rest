package ru.mav26.bosses.models.dto

import java.util.UUID

data class BossesDictionaryDto(
    val bossId: UUID,
    val bossName: String,
    val criteriaType: String,
    val criteriaValue: Int,
    val maxHp: Int,
    val resistType: String,
    val resistValue: Int,
    val bonusType: String,
    val bonusValue: Int,
    val baseDamage: Int,
    val imagePath: String
)
