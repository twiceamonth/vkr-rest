package ru.mav26.tasks.models

import java.time.LocalTime
import java.time.OffsetDateTime
import java.util.UUID

data class HabitDto(
    val habitId: UUID,
    val userLogin: String,
    val title: String,
    val difficultyId: UUID,
    val frequencyId: UUID,
    val timerInterval: LocalTime, // time
    val description: String,
    val createdAt: OffsetDateTime, // timestamptz
    val streakCount: Int,
    val lastPerformedAt: OffsetDateTime, // timestamptz
    val baseExp: Int
)
