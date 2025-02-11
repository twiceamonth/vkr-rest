package ru.mav26.character.models

import java.util.UUID

data class DialogsDto(
    val dialogId: UUID,
    val dialogText: String,
    val isForMood: Int,
)
