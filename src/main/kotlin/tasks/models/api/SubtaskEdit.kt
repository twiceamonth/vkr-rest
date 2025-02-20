package ru.mav26.tasks.models.api

import kotlinx.serialization.Serializable

@Serializable
data class SubtaskEdit(
    val title: String?= null,
    val status: Boolean?= null
)
