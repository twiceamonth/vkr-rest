package ru.mav26.events.models.db

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.date

object ActiveEventTable: Table("active_event") {
    val activeEventId = uuid("active_event_id").autoGenerate()
    val eventId = uuid("event_id").references(EventDictionaryTable.eventId)
    val startDate = date("start_date")
    val endDate = date("end_date")
    val isCompleted = bool("is_completed")

    override val primaryKey = PrimaryKey(activeEventId)
}