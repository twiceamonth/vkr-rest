package ru.mav26.auth

import io.ktor.http.*
import io.ktor.serialization.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import ru.mav26.auth.models.api.CreateAuth

fun Application.authRouting() {
    routing {
        post("/login") {
            try {
                val user = call.receive<CreateAuth>()
                call.respond(login(user))
            } catch (ex: IllegalStateException) {
                call.respond(HttpStatusCode.BadRequest)
            } catch (ex: JsonConvertException) {
                call.respond(HttpStatusCode.BadRequest)
            }
        }

        post("/register") {
            try {
                val user = call.receive<CreateAuth>()
                call.respond(register(user))
            } catch (ex: IllegalStateException) {
                call.respond(HttpStatusCode.BadRequest)
            } catch (ex: JsonConvertException) {
                call.respond(HttpStatusCode.BadRequest)
            }
        }
    }
}