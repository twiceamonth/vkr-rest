package ru.mav26.tasks.models.api

import kotlinx.serialization.Serializable

@Serializable
data class HabitCreate(
    val title: String,
    val difficulty: String,
    val frequency: String,
    val timerInterval: String, //LocalTime, // time
    val description: String,
)
