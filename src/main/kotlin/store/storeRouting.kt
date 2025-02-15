package ru.mav26.store

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import ru.mav26.Utility

fun Application.storeRouting(){
    routing {
        get("/store-items-list/{characterId}/{type}") {
            val characterId = call.parameters["characterId"]
            val type = call.parameters["type"]

            if(type == null || characterId == null) {
                call.respond(HttpStatusCode.BadRequest, "No {type} or {characterId} specified")
                return@get
            }

            if(!Utility.checkUuid(characterId)) {
                call.respond(HttpStatusCode.BadRequest, "Wrong type of {characterId}")
                return@get
            }

            call.respond(getItemsList(type, characterId))
        }

        get("/inventory/{characterId}") {
            val characterId = call.parameters["characterId"]
            if(characterId == null) {
                call.respond(HttpStatusCode.BadRequest, "No {characterId} specified")
                return@get
            }

            if(!Utility.checkUuid(characterId)) {
                call.respond(HttpStatusCode.BadRequest, "Wrong type of {characterId}")
                return@get
            }

            call.respond(getInventory(characterId))
        }

        post("/heal-button") {
            healButton()
            call.respond(HttpStatusCode.OK)
        }

        post("/buy-item/{characterId}/{itemId}") {
            val characterId = call.parameters["characterId"]
            val itemId = call.parameters["itemsId"]

            if(characterId == null || itemId == null) {
                call.respond(HttpStatusCode.BadRequest, "No {characterId} or {itemsId} specified")
                return@post
            }

            if(!Utility.checkUuid(characterId) || !Utility.checkUuid(itemId)) {
                call.respond(HttpStatusCode.BadRequest, "Wrong type of {characterId} or {itemId}")
                return@post
            }

            buyItem(characterId, itemId)
            call.respond(HttpStatusCode.OK)
        }

    }
}