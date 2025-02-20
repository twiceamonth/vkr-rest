package ru.mav26.tasks.models.db

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.time
import org.jetbrains.exposed.sql.javatime.timestampWithTimeZone
import ru.mav26.auth.models.db.UserTable
import ru.mav26.tasks.models.db.TaskTable.nullable

object HabitTable: Table("habit") {
    val habitId = uuid("habit_id").autoGenerate()
    val userLogin = varchar("user_login", 25).references(UserTable.login)
    val title = varchar("title", 50)
    val difficulty = varchar("difficulty", 15).references(DifficultyTable.difficultyName)
    val frequency = varchar("frequency", 15).references(FrequencyTable.frequencyName)
    val timerInterval = time("timer_interval").nullable()
    val description = varchar("description", 500)
    val createdAt = timestampWithTimeZone("created_at")
    val streakCount = integer("streak_count")
    val lastPerformedAt = timestampWithTimeZone("last_performed_at").nullable()
    val baseExp = integer("base_exp")

    override val primaryKey = PrimaryKey(habitId)
}