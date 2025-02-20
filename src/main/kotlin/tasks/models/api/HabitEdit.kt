package ru.mav26.tasks.models.api

import kotlinx.serialization.Serializable

@Serializable
data class HabitEdit(
    val userLogin: String? = null,
    val title: String? = null,
    val difficulty: String? = null,
    val frequency: String? = null,
    val timerInterval: String? = null,
    val description: String? = null,
    val streakCount: Int? = null,
    val lastPerformedAt: String? = null
)
