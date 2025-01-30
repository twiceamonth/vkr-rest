package ru.mav26

import io.ktor.server.application.*
import io.ktor.server.plugins.swagger.*
import io.ktor.server.routing.*

fun Application.configureDocs() {
    routing {
        swaggerUI(path = "doc")
    }
}
