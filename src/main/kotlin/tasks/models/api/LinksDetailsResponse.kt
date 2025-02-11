package ru.mav26.tasks.models.api

import kotlinx.serialization.Serializable

@Serializable
data class LinksDetailsResponse(
    val linkUrl: String,
    val linkName: String
)
