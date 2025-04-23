package ru.mav26.auth.models.db

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.datetime

object RefreshTokensTable: Table("refresh_tokens") {
    val tokenId = uuid("token_id").autoIncrement()
    val userLogin = varchar("user_id", 25).references(UserTable.login)
    val tokenHash = varchar("token_hash", 128)
    val expiresAt = datetime("expires_at")
}