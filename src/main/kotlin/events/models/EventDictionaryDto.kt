package ru.mav26.events.models

import java.util.UUID

data class EventDictionaryDto(
    val eventId: UUID,
    val eventName: String,
    val description: String,
    val eventIcon: String,
    val money: Int,
    val exp: Int,
    val criteriaType: String,
    val criteriaValue: Int
)
