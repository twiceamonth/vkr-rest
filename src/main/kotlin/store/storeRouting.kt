package ru.mav26.store

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import ru.mav26.Utility
import ru.mav26.getUserLogin

fun Application.storeRouting(repository: StoreRepository){
    routing {
        get("/store-items-list/{type}") {
            val userLogin = call.getUserLogin()
            val type = call.parameters["type"]

            if(type == null || userLogin == null) {
                call.respond(HttpStatusCode.BadRequest, "No {type} or {userLogin} specified")
                return@get
            }

            call.respond(repository.getItemsList(type, userLogin))
        }

        get("/inventory}") {
            val userLogin = call.getUserLogin()
            if(userLogin == null) {
                call.respond(HttpStatusCode.BadRequest, "No {userLogin} specified")
                return@get
            }

            call.respond(repository.getInventory(userLogin))
        }

        post("/heal-button/{characterId}") {
            val characterId = call.parameters["characterId"]

            if(characterId == null) {
                call.respond(HttpStatusCode.BadRequest, "No {characterId} specified")
                return@post
            }

            if(!Utility.checkUuid(characterId)) {
                call.respond(HttpStatusCode.BadRequest, "Wrong type of {characterId}")
                return@post
            }

            repository.healButton(characterId)
            call.respond(HttpStatusCode.OK)
        }

        post("/buy-item/{itemId}") {
            val userLogin = call.getUserLogin()
            val itemId = call.parameters["itemsId"]

            if(userLogin == null || itemId == null) {
                call.respond(HttpStatusCode.BadRequest, "No {characterId} or {itemsId} specified")
                return@post
            }

            if(!Utility.checkUuid(itemId)) {
                call.respond(HttpStatusCode.BadRequest, "Wrong type of {characterId} or {itemId}")
                return@post
            }

            repository.buyItem(userLogin, itemId)
            call.respond(HttpStatusCode.OK)
        }

    }
}