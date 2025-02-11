package ru.mav26.effects.models

import java.util.Date
import java.util.UUID

data class ActiveEffectEventDto(
    val activeEffectId: UUID,
    val userLogin: String,
    val effectEventId: UUID,
    val startDate: Date,
    val endDate: Date,
    val isCompleted: Boolean
)
