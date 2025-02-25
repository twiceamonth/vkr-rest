package ru.mav26.achievements.models.db

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.date
import ru.mav26.auth.models.db.UserTable

object AchievementsProgressTable: Table("achievements_progress") {
    val progressId = uuid("progress_id").autoGenerate()
    val userLogin = varchar("user_login", 25).references(UserTable.login)
    val progressValue = integer("progress_value")
    val isCompleted = bool("is_completed")
    val completeDate = date("complete_date").nullable()
    val achievementId = uuid("achievement_id").references(AchievementsDictionaryTable.achievementId)

    override val primaryKey = PrimaryKey(progressId)
}