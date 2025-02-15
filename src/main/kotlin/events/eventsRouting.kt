package ru.mav26.events

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import ru.mav26.Utility

fun Application.eventsRouting() {
    routing {
        get("/get-events-list") {
            call.respond(getEventsList())
        }

        get("/get-active-event") {
            call.respond(getActiveEvent())
        }

        patch("/update-event-progress/{activeEventId}") {
            val activeEventId = call.parameters["activeEventId"]

            if(activeEventId == null) {
                call.respond(HttpStatusCode.BadRequest, "No {activeEventId} specified")
                return@patch
            }

            if(!Utility.checkUuid(activeEventId)) {
                call.respond(HttpStatusCode.BadRequest, "Wrong type of {activeEventId}")
                return@patch
            }

            updateEventProgress(activeEventId)
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

            createActiveEvent(eventId)
            call.respond(HttpStatusCode.OK)
        }
    }
}