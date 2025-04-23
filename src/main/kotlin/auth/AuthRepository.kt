package ru.mav26.auth

import at.favre.lib.crypto.bcrypt.BCrypt
import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import org.jetbrains.exposed.sql.transactions.transaction
import ru.mav26.auth.models.db.RefreshTokensTable
import java.util.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import ru.mav26.auth.models.db.UserTable
import ru.mav26.auth.models.dto.RefreshTokenDto
import ru.mav26.auth.models.dto.UserDto
import java.time.LocalDate
import java.time.LocalDateTime


class AuthRepository {
    fun createUser(login: String, password: String): UserDto {
        val passwordHash = BCrypt.withDefaults().hashToString(12, password.toCharArray())
        return transaction {
            UserTable.insert {
                it[UserTable.login] = login
                it[UserTable.password] = passwordHash
                it[registeredAt] = LocalDate.now()
            }
        }.let { UserDto(
            login = login,
            password = passwordHash,
            registeredAt = LocalDate.now()
        ) }
    }

    fun findByLogin(login: String): UserDto? {
        return transaction {
            UserTable.selectAll().where(UserTable.login eq login).map {
                UserDto(
                    login = it[UserTable.login],
                    password = it[UserTable.password],
                    registeredAt = it[UserTable.registeredAt]
                )
            }.singleOrNull()
        }
    }

    fun createToken(userLogin: String, tokenHash: String, expiresAt: LocalDateTime): UUID = transaction {
        RefreshTokensTable.insert {
            it[RefreshTokensTable.userLogin] = userLogin
            it[RefreshTokensTable.tokenHash] = tokenHash
            it[RefreshTokensTable.expiresAt] = expiresAt
        }[RefreshTokensTable.tokenId]
    }

    fun findToken(tokenHash: String): RefreshTokenDto? = transaction {
        RefreshTokensTable.selectAll().where(RefreshTokensTable.tokenHash eq tokenHash)
            .map {
                RefreshTokenDto(
                    it[RefreshTokensTable.tokenId],
                    it[RefreshTokensTable.userLogin],
                    it[RefreshTokensTable.tokenHash],
                    it[RefreshTokensTable.expiresAt]
                )
            }.singleOrNull()
    }

    fun deleteToken(tokenId: UUID) = transaction {
        RefreshTokensTable.deleteWhere { RefreshTokensTable.tokenId eq tokenId }
    }
}
