package ru.mav26.tasks.models.api

import kotlinx.serialization.Serializable

@Serializable
data class TaskCreate(
    val title: String,
    val endTime: String, // OffsetDateTime, // timestamptz
    val difficulty: String,
    val priority: String,
    val frequency: String,
    val details: DetailsResponse, // TODO: С картинками вопрос, мб вообеще их убрать?
    val timerInterval: String, // LocalTime, // time
    val description: String,
    val subtasks: List<String>
)
