package ru.mav26.effects.models.db

import org.jetbrains.exposed.sql.Table

object EffectEventDictionaryTable: Table("effect_event_dictionary") {
    val effectEventId = uuid("effect_event_id").autoGenerate()
    val effectName = varchar("effect_name", 25)
    val description = varchar("description", 100)
    val effectIcon = text("effect_icon")
    val chance = integer("chance")
    val criteriaType = varchar("criteria_type", 15)
    val criteriaValue = integer("criteria_value")

    override val primaryKey = PrimaryKey(effectEventId)
}