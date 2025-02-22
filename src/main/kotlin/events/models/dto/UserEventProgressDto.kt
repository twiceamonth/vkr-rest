package ru.mav26.events.models.dto

import java.util.UUID

data class UserEventProgressDto(
    val progressId: UUID,
    val activeEventId: UUID,
    val userLogin: String,
    val progress: Int,
    val isCompleted: Boolean
)
