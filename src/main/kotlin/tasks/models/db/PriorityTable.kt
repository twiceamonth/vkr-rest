package ru.mav26.tasks.models.db

import org.jetbrains.exposed.sql.Table

object PriorityTable : Table("priority") {
    val priorityName = varchar("priority_name", 15)
    val multiplier = integer("multiplier")

    override val primaryKey = PrimaryKey(priorityName)
}