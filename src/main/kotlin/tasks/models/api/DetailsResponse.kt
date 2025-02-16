package ru.mav26.tasks.models.api

import kotlinx.serialization.Serializable

@Serializable
data class DetailsResponse(
    val linkUrl: String,
    val linkName: String,
)
