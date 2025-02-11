package ru.mav26.events.models

import java.util.Date
import java.util.UUID

data class ActiveEventDto(
    val activeEventId: UUID,
    val eventId: UUID,
    val startDate: Date,
    val endDate: Date,
    val isCompleted: Boolean
)
