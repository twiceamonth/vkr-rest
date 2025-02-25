package ru.mav26.achievements.models.db

import org.jetbrains.exposed.sql.Table

object AchievementsDictionaryTable: Table("achievements_dictionary") {
    val achievementId = uuid("achievement_id").autoGenerate()
    val title = varchar("title", 25)
    val description = varchar("description", 150)
    val resetOnEvent = bool("reset_on_event")
    val resetEventType = varchar("reset_event_type", 15)
    val criteriaType = varchar("criteria_type", 15)
    val criteriaValue = integer("criteria_value")

    override val primaryKey = PrimaryKey(achievementId)
}