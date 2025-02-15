package ru.mav26.effects.models.api

import kotlinx.serialization.Serializable

@Serializable
data class ActiveEffectResponse(
    val activeEffectId: String,
    val endDate: String, //Date
    val isCompleted: Boolean,
    val effectName: String,
    val description: String,
    val effectIcon: String,
    val criteriaType: String,
    val criteriaValue: Int
)
