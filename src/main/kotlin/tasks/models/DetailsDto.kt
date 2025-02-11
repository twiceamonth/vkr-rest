package ru.mav26.tasks.models

import java.util.UUID

data class DetailsDto(
    val detailsId: UUID,
    val linkListId: UUID,
    val imgListId: UUID
)
