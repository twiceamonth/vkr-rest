package ru.mav26.character.models

import java.util.UUID

data class BonusesDto(
    val bonusId: UUID,
    val bonusType: String,
    val description: String,
    val effect: String,
    val multiplier: Int
)
