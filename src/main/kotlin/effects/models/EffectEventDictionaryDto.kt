package ru.mav26.effects.models

import java.util.UUID

data class EffectEventDictionaryDto(
    val effectEventId: UUID,
    val effectName: String,
    val description: String,
    val effectIcon: String,
    val chance: Int,
    val criteriaType: String,
    val criteriaValue: Int
)
