package ru.mav26.tasks.models.dto

import java.time.LocalTime
import java.time.OffsetDateTime
import java.util.UUID


data class TaskDto(
    val taskId: UUID,
    val userLogin: String,
    val title: String,
    val endTime: OffsetDateTime, // timestamptz
    val difficulty: String,
    val priority: String,
    val frequency: String,
    val status: Boolean,
    val timerInterval: LocalTime, // time
    val description: String,
    val createdAt: OffsetDateTime, // timestamptz
    val finishedAt: OffsetDateTime, // timestamptz
    val baseExp: Int
)

