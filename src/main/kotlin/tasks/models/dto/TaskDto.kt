package ru.mav26.tasks.models.dto

import java.time.LocalTime
import java.time.OffsetDateTime
import java.util.UUID


data class TaskDto(
    val taskId: UUID,
    val userLogin: String,
    val title: String,
    val endTime: OffsetDateTime, // timestamptz
    val difficultyId: UUID,
    val priorityId: UUID,
    val frequencyId: UUID,
    val detailsId: UUID,
    val status: Boolean,
    val timerInterval: LocalTime, // time
    val description: String,
    val createdAt: OffsetDateTime, // timestamptz
    val finishedAt: OffsetDateTime, // timestamptz
    val baseExp: Int
)

