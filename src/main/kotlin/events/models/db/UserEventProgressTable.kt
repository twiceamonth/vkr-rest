package ru.mav26.events.models.db

import org.jetbrains.exposed.sql.Table
import ru.mav26.auth.models.db.UserTable

object UserEventProgressTable: Table("user_event_progress") {
    val progressId = uuid("progress_id").autoGenerate()
    val activeEventId = uuid("active_event_id").references(ActiveEventTable.activeEventId)
    val userLogin = varchar("user_login", 25).references(UserTable.login)
    val progress = integer("progress")
    val isCompleted = bool("is_completed")

    override val primaryKey = PrimaryKey(progressId)
}