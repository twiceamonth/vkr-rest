package ru.mav26.achievements

import ru.mav26.achievements.models.api.AchievementProgressResponse
import ru.mav26.achievements.models.api.AchievementResponse

fun getAchievementsList(): List<AchievementResponse> {

}

fun getAchievementsProgress(userLogin: String): List<AchievementProgressResponse> {

}

fun startAchievementProgress(userLogin: String, achievementId: String) {

}

fun updateAchievementProgress(progressId: String, userLogin: String) {

}

fun resetProgress(progressId: String, userLogin: String) {

}