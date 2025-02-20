package ru.mav26.tasks.models.dto

import java.util.UUID

data class DetailsDto(
    val detailsId: UUID,
    val linkUrl: String,
    val linkName: String,
    val taskId: UUID
)
