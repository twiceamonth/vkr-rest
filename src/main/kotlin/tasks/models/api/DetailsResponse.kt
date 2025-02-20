package ru.mav26.tasks.models.api

import kotlinx.serialization.Serializable

@Serializable
data class DetailsResponse(
    val detailsId: String,
    val linkUrl: String,
    val linkName: String,
)
