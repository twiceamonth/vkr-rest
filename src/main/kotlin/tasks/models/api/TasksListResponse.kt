package ru.mav26.tasks.models.api

import kotlinx.serialization.Serializable

@Serializable
data class TasksListResponse(
    val taskId: String,
    val title: String,
    val endTime: String? = null, //OffsetDateTime, // timestamptz
    val difficulty: String,
    val priority: String,
    val frequency: String,
    val status: Boolean,
    val timerInterval: String? = null, //LocalTime, // time
    val description: String,
    val subtasks: List<SubtaskResponse> = emptyList()
)
