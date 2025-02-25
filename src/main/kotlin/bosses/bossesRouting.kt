package ru.mav26.bosses

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import ru.mav26.Utility

fun Application.bossesRouting(repository: BossesRepository) {
    routing {
        get("/get-active-boss/{userLogin}") {
            val userLogin = call.parameters["userLogin"]

            if(userLogin == null) {
                call.respond(HttpStatusCode.BadRequest, "No {userLogin} specified")
                return@get
            }
            val response = repository.getActiveBoss(userLogin)
            if(response == null) call.respond(HttpStatusCode.NotFound, "No active boss") else call.respond(response)
        }

        get("/get-bosses-list") {
            call.respond(repository.getBossesList())
        }

        post("/make-damage/{userLogin}/{tDIff}") {
            val userLogin = call.parameters["userLogin"]
            val tDIff = call.parameters["tDIff"]

            if(userLogin == null || tDIff == null) {
                call.respond(HttpStatusCode.BadRequest, "No {userLogin} specified")
                return@post
            }

            call.respond(repository.makeDamage(userLogin, tDIff))
        }

        post("/create-active-boss/{userLogin}/{bossId}") {
            val userLogin = call.parameters["userLogin"]
            val bossId = call.parameters["bossId"]

            if(userLogin == null || bossId == null) {
                call.respond(HttpStatusCode.BadRequest, "No {userLogin} or {bossId} specified")
                return@post
            }

            if(!Utility.checkUuid(bossId)) {
                call.respond(HttpStatusCode.BadRequest, "Wrong {bossId} type")
                return@post
            }

            repository.createActiveBoss(userLogin, bossId)
            call.respond(HttpStatusCode.OK)
        }
    }
}