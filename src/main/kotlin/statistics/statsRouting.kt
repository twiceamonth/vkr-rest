package ru.mav26.statistics

import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import ru.mav26.getUserLogin
import java.time.LocalDate

fun Application.statsRouting(repo: StatsRepository) {
    routing {
        authenticate("jwt") {
            get("/summary") {
                val userLogin = call.getUserLogin()
                call.respond(repo.totalByDifficulty(userLogin))
            }
            get("/avg-time") {
                val userLogin = call.getUserLogin()
                call.respond(repo.avgTimeByDifficulty(userLogin))
            }
            get("/daily") {
                val userLogin = call.getUserLogin()
                val year  = call.request.queryParameters["year"]?.toIntOrNull() ?: LocalDate.now().year
                val month = call.request.queryParameters["month"]?.toIntOrNull() ?: LocalDate.now().monthValue
                call.respond(repo.dailyCounts(userLogin, year, month))
            }
        }
    }
}