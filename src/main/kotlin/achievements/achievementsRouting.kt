package ru.mav26.achievements

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import ru.mav26.Utility

fun Application.achievementsRouting() {
    routing {
        get("/get-achievements-list") {
            call.respond(getAchievementsList())
        }

        get("/get-achievements-progress/{userLogin}") {
            val userLogin = call.parameters["userLogin"]

            if(userLogin == null) {
                call.respond(HttpStatusCode.BadRequest, "No {userLogin} specified")
                return@get
            }

            call.respond(getAchievementsProgress(userLogin))
        }

        post("/start-achievement-progress/{achievementId}/{userLogin}") {
            val userLogin = call.parameters["userLogin"]
            val achievementId = call.parameters["achievementId"]

            if(userLogin == null || achievementId == null) {
                call.respond(HttpStatusCode.BadRequest, "No {userLogin} or {achievementId} specified")
                return@post
            }

            if(!Utility.checkUuid(achievementId)) {
                call.respond(HttpStatusCode.BadRequest, "Wrong {achievementId} type")
                return@post
            }

            startAchievementProgress(userLogin, achievementId)
            call.respond(HttpStatusCode.OK)
        }

        post("/update-achievement-progress/{progressId}/{userLogin}") {
            val userLogin = call.parameters["userLogin"]
            val progressId = call.parameters["progressId"]

            if(userLogin == null || progressId == null) {
                call.respond(HttpStatusCode.BadRequest, "No {userLogin} or {progressId} specified")
                return@post
            }

            if(!Utility.checkUuid(progressId)) {
                call.respond(HttpStatusCode.BadRequest, "Wrong {progressId} type")
                return@post
            }

            updateAchievementProgress(progressId, userLogin)
            call.respond(HttpStatusCode.OK)
        }

        post("/reset-progress/{progressId}/{userLogin}") {
            val userLogin = call.parameters["userLogin"]
            val progressId = call.parameters["progressId"]

            if(userLogin == null || progressId == null) {
                call.respond(HttpStatusCode.BadRequest, "No {userLogin} or {progressId} specified")
                return@post
            }

            if(!Utility.checkUuid(progressId)) {
                call.respond(HttpStatusCode.BadRequest, "Wrong {progressId} type")
                return@post
            }

            resetProgress(progressId, userLogin)
            call.respond(HttpStatusCode.OK)
        }
    }
}