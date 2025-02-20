package ru.mav26.tasks.models.db


import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.time
import org.jetbrains.exposed.sql.javatime.timestampWithTimeZone
import ru.mav26.auth.models.db.UserTable
import java.time.OffsetDateTime

object TaskTable : Table("task") {
    val taskId = uuid("task_id").autoGenerate()
    val userLogin = varchar("user_login", 25).references(UserTable.login)
    val title = varchar("title", 50)
    val endTime = timestampWithTimeZone("end_time").nullable()
    val difficulty = varchar("difficulty", 15).references(DifficultyTable.difficultyName)
    val priority = varchar("priority", 15).references(PriorityTable.priorityName)
    val frequency = varchar("frequency", 15).references(FrequencyTable.frequencyName)
    val status = bool("status").default(false)
    val timerInterval = time("timer_interval").nullable()
    val description = varchar("description", 500)
    val createdAt = timestampWithTimeZone("created_at").default(OffsetDateTime.now())
    val finishedAt = timestampWithTimeZone("finished_at").nullable()
    val baseExp = integer("base_exp")

    override val primaryKey = PrimaryKey(taskId)
}