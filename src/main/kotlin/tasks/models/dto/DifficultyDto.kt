package ru.mav26.tasks.models.dto

import java.util.UUID

data class DifficultyDto(
    val difficultyId: UUID,
    val difficultyName: String,
    val multiplier: Int
)
