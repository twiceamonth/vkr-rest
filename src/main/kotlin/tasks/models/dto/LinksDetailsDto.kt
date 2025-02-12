package ru.mav26.tasks.models.dto

import java.util.UUID

data class LinksDetailsDto(
    val linkId: UUID,
    val linkUrl: String,
    val linkName: String
)
