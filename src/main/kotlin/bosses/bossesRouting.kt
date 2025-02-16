package ru.mav26.bosses

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import ru.mav26.Utility

fun Application.bossesRouting() {
    routing {
        get("/get-active-boss/{userLogin}") {
            val userLogin = call.parameters["userLogin"]

            if(userLogin == null) {
                call.respond(HttpStatusCode.BadRequest, "No {userLogin} specified")
                return@get
            }

            call.respond(getActiveBoss(userLogin))
        }

        get("/get-bosses-list") {
            call.respond(getBossesList())
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

            createActiveBoss(userLogin, bossId)
            call.respond(HttpStatusCode.OK)
        }
    }
}