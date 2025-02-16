package ru.mav26.achievements.models.dto

import java.util.*

data class AchievementProgressDto(
    val progressId: UUID,
    val achievementId: UUID,
    val userLogin: String,
    val progressValue: Int,
    val isCompleted: Boolean,
    val completeDate: Date?
)
