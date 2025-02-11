package ru.mav26.achievements.models

import java.util.Date
import java.util.UUID

data class AchievementsDto(
    val achievementId: UUID,
    val userLogin: String,
    val characterId: UUID,
    val title: String,
    val description: String,
    val progressValue: Int,
    val resetOnEvent: Boolean,
    val resentEventType: String,
    val isCompleted: Boolean,
    val completeDate: Date,
    val criteriaType: String,
    val criteriaValue: Int
)
