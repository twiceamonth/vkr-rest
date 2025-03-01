package ru.mav26.character.models.db

import org.jetbrains.exposed.sql.Table

object CharacterTypeTable: Table("character_type") {
    val characterType = varchar("character_type", 25)
    val imagePath = text("image_path")
    val bonusId = uuid("bonus_id").references(BonusesTable.bonusId)

    override val primaryKey = PrimaryKey(characterType)
}