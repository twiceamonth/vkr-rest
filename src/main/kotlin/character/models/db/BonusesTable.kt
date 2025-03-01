package ru.mav26.character.models.db

import org.jetbrains.exposed.sql.Table

object BonusesTable: Table("bonuses") {
    val bonusId = uuid("bonus_id")
    val bonusType = varchar("bonus_type", 15)
    val description = varchar("description", 200)
    val effect = varchar("effect", 15)
    val multiplier = integer("multiplier")

    override val primaryKey = PrimaryKey(bonusId)
}