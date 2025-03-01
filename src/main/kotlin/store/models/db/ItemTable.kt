package ru.mav26.store.models.db

import org.jetbrains.exposed.sql.Table
import ru.mav26.auth.models.db.UserTable

object ItemTable: Table("item") {
    val itemId = uuid("item_id").autoGenerate()
    val storeId = uuid("store_id")
    val userLogin = varchar("user_login", 25).references(UserTable.login)

    override val primaryKey = PrimaryKey(itemId)
}