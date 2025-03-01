package ru.mav26.character.models.api

import kotlinx.serialization.Serializable

@Serializable
data class CharacterStats(
    val level: Int? = null,
    val maxHp: Int? = null,
    val currentHp: Int? = null,
    val exp: Int? = null,
    val coins: Int? = null,
    val stressCoef: Int? = null,
    val isDead: Boolean? = null,
    val baseDamage: Int? = null,
    val deadAt: String? = null,
    val moodLevel: Int? = null
)
