package ru.mav26.character.models.api

import kotlinx.serialization.Serializable

@Serializable
data class CharacterStats(
    val level: Int?,
    val maxHp: Int?,
    val currentHp: Int?,
    val exp: Int?,
    val coins: Int?,
    val stressCoef: Int?,
    val isDead: Boolean?,
    val baseDamage: Int?,
    val deadAt: String?,
    val moodLevel: Int?
)
