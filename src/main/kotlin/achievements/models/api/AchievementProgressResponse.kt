package ru.mav26.achievements.models.api

import kotlinx.serialization.Serializable

@Serializable
data class AchievementProgressResponse(
    val userLogin: String,
    val achievementId: String,
    val progressId: String,
    val title: String,
    val description: String,
    val resetOnEvent: Boolean,
    val resentEventType: String,
    val criteriaType: String,
    val criteriaValue: Int,
    val progressValue: Int,
    val isCompleted: Boolean,
    val completeDate: String?
)
