package ru.mav26.tasks.models.api

import kotlinx.serialization.Serializable

@Serializable
data class PriorityResponse(
    val priorityName: String,
    val multiplier: Int
)
