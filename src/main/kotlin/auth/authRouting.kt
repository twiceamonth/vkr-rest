package ru.mav26.auth

import at.favre.lib.crypto.bcrypt.BCrypt
import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import io.ktor.http.*
import io.ktor.serialization.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import ru.mav26.Consts
import ru.mav26.auth.models.api.AuthRequest
import ru.mav26.auth.models.api.RefreshTokenRequest
import ru.mav26.auth.models.api.TokensResponse
import java.security.MessageDigest
import java.time.LocalDateTime
import java.util.*

fun Application.authRouting(repository: AuthRepository) {
    routing {
        post("/login") {
            try {
                val authData = call.receive<AuthRequest>()
                val user = repository.findByLogin(authData.userLogin)

                if(user == null) {
                    call.respond(HttpStatusCode.BadRequest, "User not found")
                    return@post
                }

                if(!BCrypt.verifyer().verify(authData.password.toCharArray(), user.password).verified) {
                    call.respond(HttpStatusCode.Unauthorized, "Invalid credentials")
                    return@post
                }

                val refreshToken = generateRefreshToken()
                val refreshTokenHash = hashToken(refreshToken)
                val expiresAt = LocalDateTime.now().plusDays(30)
                repository.createToken(user.login, refreshTokenHash, expiresAt)

                call.respond(
                    TokensResponse(
                        accessToken = generateAccessToken(user.login),
                        refreshToken = refreshToken
                    )
                )
            } catch (ex: IllegalStateException) {
                call.respond(HttpStatusCode.BadRequest)
            } catch (ex: JsonConvertException) {
                call.respond(HttpStatusCode.BadRequest)
            }
        }

        post("/register") {
            try {
                val authData = call.receive<AuthRequest>()
                if(repository.findByLogin(authData.userLogin) != null) {
                    call.respond(HttpStatusCode.Conflict, "User already exist")
                    return@post
                }

                val user = repository.createUser(authData.userLogin, authData.password)
                val refreshToken = generateRefreshToken()
                val refreshTokenHash = hashToken(refreshToken)
                val expiresAt = LocalDateTime.now().plusDays(30)
                repository.createToken(authData.userLogin, refreshTokenHash, expiresAt)

                call.respond(
                    TokensResponse(
                        accessToken = generateAccessToken(authData.userLogin),
                        refreshToken = refreshToken
                    )
                )
            } catch (ex: IllegalStateException) {
                call.respond(HttpStatusCode.BadRequest)
            } catch (ex: JsonConvertException) {
                call.respond(HttpStatusCode.BadRequest)
            }
        }

        post("/refresh") {
            try {
                val refreshRequest = call.receive<RefreshTokenRequest>()
                val refreshTokenHash = hashToken(refreshRequest.refreshToken)
                val token = repository.findToken(refreshTokenHash)

                if(token == null) {
                    call.respond(HttpStatusCode.BadRequest, "Invalid token")
                    return@post
                }

                if(token.expiresAt.isBefore(LocalDateTime.now())) {
                    call.respond(HttpStatusCode.Unauthorized, "Token has expired")
                    return@post
                }

                repository.deleteToken(token.tokenId)

                val newRefreshToken = generateRefreshToken()
                val newRefreshTokenHash = hashToken(newRefreshToken)
                val expiresAt = LocalDateTime.now().plusDays(30)
                repository.createToken(token.userId, newRefreshTokenHash, expiresAt)

                call.respond(
                    TokensResponse(
                        accessToken = generateAccessToken(token.userId),
                        refreshToken = newRefreshToken
                    )
                )
            } catch (ex: Exception) {
                call.respond(HttpStatusCode.BadRequest, ex.message ?: "Ошибка запроса")
            }
        }
    }
}

private fun generateAccessToken(userLogin: String): String {
    return JWT.create()
        .withSubject("Authentication")
        .withIssuer(Consts.issuer)
        .withAudience(Consts.audience)
        .withClaim("userLogin", userLogin)
        .withExpiresAt(Date(System.currentTimeMillis() + 15 * 60 * 1000)) // 15 минут
        .sign(Algorithm.HMAC256(Consts.secret))
}

private fun generateRefreshToken(): String = UUID.randomUUID().toString()

private fun hashToken(token: String): String {
    return MessageDigest
        .getInstance("SHA-256")
        .digest(token.toByteArray())
        .fold("") { str, it -> str + "%02x".format(it) }
}