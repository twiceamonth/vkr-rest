package ru.mav26.tasks.models

import java.util.UUID

data class PriorityDto(
    val priorityId: UUID,
    val priorityName: String,
    val multiplier: Int
)
