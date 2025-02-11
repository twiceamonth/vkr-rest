package ru.mav26.character.models

import java.util.UUID

data class CharacterTypeDto(
    val characterType: String,
    val imagePath: String,
    val bonusId: UUID
)
