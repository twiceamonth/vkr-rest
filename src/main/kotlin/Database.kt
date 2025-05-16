package ru.mav26

import io.ktor.server.application.*
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

fun Application.configureDatabase() {
    Database.connect(
        "jdbc:postgresql://vkr-db:5432/postgres?currentSchema=vkr",
        user = "postgres",
        password = "postgres"
    )
}

suspend fun <T> dbQuery(block: () -> T): T =
    newSuspendedTransaction(Dispatchers.IO) { block() }