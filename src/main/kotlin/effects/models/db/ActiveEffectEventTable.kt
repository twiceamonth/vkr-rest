package ru.mav26.effects.models.db

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.date

object ActiveEffectEventTable: Table("active_effect_event") {
    val activeEffectId = uuid("active_effect_id").autoGenerate()
    val effectEventId = uuid("effect_event_id").references(EffectEventDictionaryTable.effectEventId)
    val startDate = date("start_date")
    val endDate = date("end_date")
    val isCompleted = bool("is_completed")

    override val primaryKey = PrimaryKey(activeEffectId)
}