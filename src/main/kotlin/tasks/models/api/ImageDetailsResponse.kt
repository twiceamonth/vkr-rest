package ru.mav26.tasks.models.api

import kotlinx.serialization.Serializable

@Serializable
data class ImageDetailsResponse(
    val imagePath: String
)
