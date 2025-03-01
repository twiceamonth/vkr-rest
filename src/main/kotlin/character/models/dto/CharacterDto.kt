package ru.mav26.character.models.dto

import java.time.OffsetDateTime
import java.util.*

data class CharacterDto(
    val characterId: UUID,
    val userLogin: String,
    val characterName: String,
    val gender: Boolean,
    val hairId: UUID,
    val chestplateId: UUID,
    val footsId: UUID,
    val legsId: UUID,
    val backgroundId: UUID,
    val characterType: String,
    val level: Int,
    val maxHp: Int,
    val currentHp: Int,
    val exp: Int,
    val coins: Int,
    val stressCoef: Int,
    val isDead: Boolean,
    val baseDamage: Int,
    val createdAt: OffsetDateTime,
    val deadAt: OffsetDateTime?,
    val moodLevel: Int
)
