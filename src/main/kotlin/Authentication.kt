package ru.mav26

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*

fun Application.configureAuthentication() {
    install(Authentication) {
        jwt("jwt") {
            realm = Consts.realm
            verifier(
                JWT.require(Algorithm.HMAC256(Consts.secret))
                    .withIssuer(Consts.issuer)
                    .withAudience(Consts.audience)
                    .build()
            )
            validate { credential ->
                val userLogin = credential.payload.getClaim("userLogin").asString()
                if (!userLogin.isNullOrEmpty()) {
                    JWTPrincipal(credential.payload)
                } else {
                    null
                }
            }
        }
    }
}

fun ApplicationCall.getUserLogin() : String {
    val principal = principal<JWTPrincipal>()
        ?: throw IllegalArgumentException("Missing principal")

    return principal.payload.getClaim("userLogin").asString()
        ?: throw IllegalArgumentException("Missing userLogin claim")
}