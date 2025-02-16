package ru.mav26.tasks.models.api

import kotlinx.serialization.Serializable

@Serializable
data class TaskCreate(
    val title: String,
    val endTime: String, // OffsetDateTime, // timestamptz
    val difficulty: String,
    val priority: String,
    val frequency: String,
    val details: List<DetailsResponse>,
    val timerInterval: String, // LocalTime, // time
    val description: String,
    val subtasks: List<String>
)
