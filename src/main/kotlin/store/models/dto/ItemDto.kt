package ru.mav26.store.models.dto

import java.util.UUID

data class ItemDto(
    val itemId: UUID,
    val title: String,
    val type: String,
    val imagePath: String,
    val description: String
)
