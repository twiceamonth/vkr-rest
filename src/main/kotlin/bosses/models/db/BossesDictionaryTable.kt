package ru.mav26.bosses.models.db

import org.jetbrains.exposed.sql.Table

object BossesDictionaryTable: Table("bosses_dictionary") {
    val bossId = uuid("boss_id").autoGenerate()
    val bossName = varchar("boss_name", 20)
    val criteriaType = varchar("criteria_type", 15)
    val criteriaValue = integer("criteria_value")
    val maxHp = integer("max_hp")
    val resistType = varchar("resist_type", 15)
    val resistValue = integer("resist_value")
    val bonusType = varchar("bonus_type", 15)
    val bonusValue = integer("bonus_value")
    val baseDamage = integer("base_damage").default(1)
    val imagePath = text("image_path")

    override val primaryKey = PrimaryKey(bossId)
}