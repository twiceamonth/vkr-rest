package ru.mav26.effects.models.dto

import java.time.LocalDate
import java.util.Date
import java.util.UUID

data class ActiveEffectEventDto(
    val activeEffectId: UUID,
    val effectEventId: UUID,
    val startDate: LocalDate,
    val endDate: LocalDate,
    val isCompleted: Boolean
)
