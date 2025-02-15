package ru.mav26.events.models.api

import kotlinx.serialization.Serializable

@Serializable
data class ActiveEventResponse(
    val activeEventId: String,
    val eventName: String,
    val description: String,
    val eventIcon: String,
    val money: Int,
    val exp: Int,
    val criteriaType: String,
    val criteriaValue: Int,
    val endDate: String, //Date
    val isCompleted: Boolean,
    val progress: Int
)
