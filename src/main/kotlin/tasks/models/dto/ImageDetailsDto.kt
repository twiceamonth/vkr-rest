package ru.mav26.tasks.models.dto

import java.util.UUID

data class ImageDetailsDto(
    val imageId: UUID,
    val imagePath: String
)
