package ru.mav26.store.models.db

import org.jetbrains.exposed.sql.Table

object ItemTable: Table("item") {
    val itemId = uuid("item_id").autoGenerate()
    val storeId = uuid("store_id")
    val characterId = uuid("character_id")

    override val primaryKey = PrimaryKey(itemId)
}