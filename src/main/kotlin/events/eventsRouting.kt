package ru.mav26.events

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import ru.mav26.Utility

fun Application.eventsRouting(repository: EventsRepository) {
    routing {
        get("/get-events-list") {
            call.respond(repository.getEventsList())
        }

        get("/get-active-event/{userLogin}") {
            val userLogin = call.parameters["userLogin"]

            if(userLogin == null) {
                call.respond(HttpStatusCode.BadRequest, "No {userLogin} specified")
                return@get
            }

            val response = repository.getActiveEvent(userLogin)
            if(response == null) call.respond(HttpStatusCode.NotFound, "No active events") else call.respond(response)
        }

        patch("/update-event-progress/{activeEventId}/{userLogin}/{type}") {
            val activeEventId = call.parameters["activeEventId"]
            val userLogin = call.parameters["userLogin"]
            val type = call.parameters["type"]

            if(activeEventId == null || userLogin == null || type == null) {
                call.respond(HttpStatusCode.BadRequest, "No {activeEventId} or {userLogin} or {type} specified")
                return@patch
            }

            if(!Utility.checkUuid(activeEventId) && (type == "p" || type == "m")) {
                call.respond(HttpStatusCode.BadRequest, "Wrong type of {activeEventId} of {type}")
                return@patch
            }

            repository.updateEventProgress(activeEventId, userLogin, type)
            call.respond(HttpStatusCode.OK)
        }

        post("/create-active-event/{eventId}") {
            val eventId = call.parameters["eventId"]

            if(eventId == null) {
                call.respond(HttpStatusCode.BadRequest, "No {eventId} specified")
                return@post
            }

            if(!Utility.checkUuid(eventId)) {
                call.respond(HttpStatusCode.BadRequest, "Wrong type of {eventId}")
                return@post
            }

            repository.createActiveEvent(eventId)
            call.respond(HttpStatusCode.OK)
        }
    }
}