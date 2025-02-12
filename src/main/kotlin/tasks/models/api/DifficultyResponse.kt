package ru.mav26.tasks.models.api

import kotlinx.serialization.Serializable

@Serializable
data class DifficultyResponse(
    val difficultyName: String,
    val multiplier: Int
)
