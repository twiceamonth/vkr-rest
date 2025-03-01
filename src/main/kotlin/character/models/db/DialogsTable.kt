package ru.mav26.character.models.db

import org.jetbrains.exposed.sql.Table

object DialogsTable: Table("dialogs") {
    val dialogId = uuid("dialog_id")
    val dialogText = text("dialog_text")
    val isForMood = integer("is_for_mood")

    override val primaryKey = PrimaryKey(dialogId)
}