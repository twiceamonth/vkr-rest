package ru.mav26.store

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import ru.mav26.Utility

fun Application.storeRouting(repository: StoreRepository){
    routing {
        get("/store-items-list/{userLogin}/{type}") {
            val userLogin = call.parameters["userLogin"]
            val type = call.parameters["type"]

            if(type == null || userLogin == null) {
                call.respond(HttpStatusCode.BadRequest, "No {type} or {userLogin} specified")
                return@get
            }

            call.respond(repository.getItemsList(type, userLogin))
        }

        get("/inventory/{userLogin}") {
            val userLogin = call.parameters["userLogin"]
            if(userLogin == null) {
                call.respond(HttpStatusCode.BadRequest, "No {userLogin} specified")
                return@get
            }

            call.respond(repository.getInventory(userLogin))
        }

        post("/heal-button") {
            repository.healButton()
            call.respond(HttpStatusCode.OK)
        }

        post("/buy-item/{userLogin}/{itemId}") {
            val userLogin = call.parameters["userLogin"]
            val itemId = call.parameters["itemsId"]

            if(userLogin == null || itemId == null) {
                call.respond(HttpStatusCode.BadRequest, "No {characterId} or {itemsId} specified")
                return@post
            }

            if( !Utility.checkUuid(itemId)) {
                call.respond(HttpStatusCode.BadRequest, "Wrong type of {characterId} or {itemId}")
                return@post
            }

            repository.buyItem(userLogin, itemId)
            call.respond(HttpStatusCode.OK)
        }

    }
}