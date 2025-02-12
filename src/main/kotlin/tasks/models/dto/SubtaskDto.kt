package ru.mav26.tasks.models.dto

import java.util.UUID

data class SubtaskDto(
    val subtaskId: UUID,
    val title: String,
    val status: Boolean,
    val taskId: UUID
)
