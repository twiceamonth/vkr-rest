package ru.mav26.character.models.db

import org.jetbrains.exposed.sql.Table

object LevelTable: Table("level") {
    val level = integer("level")
    val exp = integer("exp")
    val maxHp = integer("max_hp")

    override val primaryKey = PrimaryKey(level)
}