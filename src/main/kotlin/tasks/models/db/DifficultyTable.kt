package ru.mav26.tasks.models.db

import org.jetbrains.exposed.sql.Table

object DifficultyTable : Table("difficulty") {
    val difficultyName = varchar("difficulty_name", 15)
    val multiplier = integer("multiplier")

    override val primaryKey = PrimaryKey(difficultyName)
}
