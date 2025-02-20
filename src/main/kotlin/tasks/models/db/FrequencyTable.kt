package ru.mav26.tasks.models.db

import org.jetbrains.exposed.sql.Table

object FrequencyTable: Table("frequency") {
    val frequencyName = varchar("frequency_name", 15)

    override val primaryKey = PrimaryKey(frequencyName)
}