package ru.mav26.character.models.api

import kotlinx.serialization.Serializable

@Serializable
data class CharacterTypesResponse(
    val characterType: String,
    val imagePath: String,
    val bonusType: String,
    val description: String,
    val effect: String,
    val multiplier: Int
)
