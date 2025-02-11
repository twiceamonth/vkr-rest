package ru.mav26.tasks.models

import java.util.UUID

data class ImageDetailsDto(
    val imageId: UUID,
    val imagePath: String
)
