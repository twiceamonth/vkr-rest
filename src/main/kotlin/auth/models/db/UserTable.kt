package ru.mav26.auth.models.db

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.date

object UserTable: Table("user") {
    val login = varchar("login", 25)
    val password = varchar("password", 25)
    val registeredAt = date("registered_at")

    override val primaryKey = PrimaryKey(login)
}