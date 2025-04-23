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
                    .build()
            )
            validate { credential ->
                val login = credential.payload.subject
                if (login != null) {
                    JWTPrincipal(credential.payload)
                } else {
                    null
                }
            }
        }
    }
}