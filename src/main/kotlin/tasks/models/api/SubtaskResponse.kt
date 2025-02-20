package ru.mav26.tasks.models.api

import kotlinx.serialization.Serializable


@Serializable
data class SubtaskResponse(
    val subtaskId: String,
    val title: String,
    val status: Boolean,
    val taskId: String
)
