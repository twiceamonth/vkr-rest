package ru.mav26.events.models.dto

import java.time.LocalDate
import java.util.Date
import java.util.UUID

data class ActiveEventDto(
    val activeEventId: UUID,
    val eventId: UUID,
    val startDate: LocalDate,
    val endDate: LocalDate,
    val isCompleted: Boolean
)
