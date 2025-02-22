package ru.mav26.events.models.db

import org.jetbrains.exposed.sql.Table

object EventDictionaryTable: Table("event_dictionary") {
    val eventId = uuid("event_id").autoGenerate()
    val eventName = varchar("event_name", 25)
    val description = varchar("description", 100)
    val eventIcon = text("event_icon")
    val money = integer("money")
    val exp = integer("exp")
    val criteriaType = varchar("criteria_type", 15)
    val criteriaValue = integer("criteria_value")

    override val primaryKey = PrimaryKey(eventId)
}