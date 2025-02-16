package ru.mav26.tasks.models.dto

import java.time.LocalTime
import java.time.OffsetDateTime
import java.util.UUID

data class HabitDto(
    val habitId: UUID,
    val userLogin: String,
    val title: String,
    val difficulty: String,
    val frequency: String,
    val timerInterval: LocalTime, // time
    val description: String,
    val createdAt: OffsetDateTime, // timestamptz
    val streakCount: Int,
    val lastPerformedAt: OffsetDateTime, // timestamptz
    val baseExp: Int
)
