package ru.mav26.tasks.models.db

import org.jetbrains.exposed.sql.Table


object DetailsTable: Table("details") {
    val detailsId = uuid("details_id").autoGenerate()
    val linkUrl = text("link_url")
    val linkName = varchar("link_name", 25)
    val taskId = uuid("task_id").references(TaskTable.taskId)

    override val primaryKey = PrimaryKey(detailsId)
}