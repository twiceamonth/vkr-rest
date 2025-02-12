package ru.mav26

import io.ktor.server.application.*
import org.jetbrains.exposed.sql.Database

fun Application.configureDatabase() {
    Database.connect(
        "jdbc:postgresql://localhost:5432/postgres",
        user = "postgres",
        password = "postgres"
    )
}