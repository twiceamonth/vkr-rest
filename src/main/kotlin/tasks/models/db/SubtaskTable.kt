package ru.mav26.tasks.models.db

import org.jetbrains.exposed.sql.Table

object SubtaskTable: Table("subtask") {
    val subtaskId = uuid("subtask_id").autoGenerate()
    val title = varchar("title", 50)
    val taskId = uuid("task_id").references(TaskTable.taskId)
    val status = bool("status").default(false)

    override val primaryKey = PrimaryKey(subtaskId)
}