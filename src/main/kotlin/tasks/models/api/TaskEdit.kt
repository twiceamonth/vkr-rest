package ru.mav26.tasks.models.api

import kotlinx.serialization.Serializable

@Serializable
data class TaskEdit(
    val title: String? = null,
    val endTime: String? = null, // OffsetDateTime, // timestamptz
    val difficulty: String? = null,
    val priority: String? = null,
    val frequency: String? = null,
    val status: Boolean? = null,
    val timerInterval: String? = null, // LocalTime, // time
    val description: String? = null,
    val finishedAt: String? = null, //OffsetDateTime, // timestamptz
    val subtasks: List<SubtaskCreate> = emptyList()
)
