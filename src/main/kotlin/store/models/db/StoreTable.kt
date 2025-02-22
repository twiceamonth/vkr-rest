package ru.mav26.store.models.db

import org.jetbrains.exposed.sql.Table

object StoreTable: Table("store") {
    val storeId = uuid("store_id")
    val cost = integer("cost")
    val lvlToBuy = integer("lvl_to_buy")
    val title = varchar("title", 25)
    val type = varchar("type", 10)
    val imagePath = text("image_path")
    val description = varchar("description", 200)

    override val primaryKey = PrimaryKey(storeId)
}