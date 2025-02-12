package ru.mav26.tasks.models.api

import kotlinx.serialization.Serializable

@Serializable
data class TaskDetailsResponse(
    val taskId: String, //UUID,
    val title: String,
    val endTime: String, // OffsetDateTime, // timestamptz
    val difficulty: DifficultyResponse,
    val priority: PriorityResponse,
    val frequency: FrequencyResponse,
    val details: DetailsResponse,
    val status: Boolean,
    val timerInterval: String, // LocalTime, // time
    val description: String,
    val createdAt: String, //OffsetDateTime, // timestamptz
    val finishedAt: String, //OffsetDateTime, // timestamptz
    val subtasks: List<SubtaskResponse>
)
