package ru.mav26.effects

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import ru.mav26.Utility

fun Application.effectsRouting(repository: EffectsRepository) {
    routing {
        get("/get-active-effect") {
            call.respond(repository.getActiveEffect())
        }

        get("/get-effects-list") {
            call.respond(repository.getEffectsList())
        }

        post("/create-active-effect/{effectId}") {
            val effectId = call.parameters["effectId"]

            if(effectId == null) {
                call.respond(HttpStatusCode.BadRequest, "No {effectId} specified")
                return@post
            }

            if(!Utility.checkUuid(effectId)) {
                call.respond(HttpStatusCode.BadRequest, "Wrong {effectId} type")
                return@post
            }

            repository.createActiveEffect(effectId)
            call.respond(HttpStatusCode.OK)
        }
    }
}