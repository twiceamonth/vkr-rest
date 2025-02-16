package ru.mav26.achievements.models.dto

import java.util.UUID

data class AchievementDictionaryDto(
    val achievementId: UUID,
    val title: String,
    val description: String,
    val resetOnEvent: Boolean,
    val resentEventType: String,
    val criteriaType: String,
    val criteriaValue: Int
)
