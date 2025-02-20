package ru.mav26.tasks.models.api

import kotlinx.serialization.Serializable

@Serializable
data class DetailsEdit(
    val linkUrl: String? = null,
    val linkName: String? = null
)
