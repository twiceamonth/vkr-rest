package ru.mav26.character

import io.ktor.http.*
import io.ktor.serialization.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import ru.mav26.Utility
import ru.mav26.character.models.api.CharacterItems
import ru.mav26.character.models.api.CharacterStats
import ru.mav26.character.models.api.CreateCharacter

fun Application.characterRouting() {
    routing {
        get("/get-character/{userLogin}") {
            val userLogin = call.parameters["userLogin"]

            if(userLogin == null) {
                call.respond(HttpStatusCode.BadRequest, "No {userLogin} specified")
                return@get
            }

            call.respond(getCharacter(userLogin))
        }

        get("/get-all-characters/{userLogin}") {
            val userLogin = call.parameters["userLogin"]

            if(userLogin == null) {
                call.respond(HttpStatusCode.BadRequest, "No {userLogin} specified")
                return@get
            }

            call.respond(getAllCharacters(userLogin))
        }

        get("/get-character-types") {
            call.respond(getCharacterTypes())
        }

        get("/get-random-dialog") {
            call.respond(getRandomDialog())
        }

        post("/create-character/{userLogin}") {
            val userLogin = call.parameters["userLogin"]

            if(userLogin == null) {
                call.respond(HttpStatusCode.BadRequest, "No {userLogin} specified")
                return@post
            }

            try {
                val newCharacter = call.receive<CreateCharacter>()
                call.respond(createCharacter(newCharacter, userLogin))
            } catch (ex: IllegalStateException) {
                call.respond(HttpStatusCode.BadRequest)
            } catch (ex: JsonConvertException) {
                call.respond(HttpStatusCode.BadRequest)
            }
        }

        patch("/edit-character-items/{characterId}") {
            val characterId = call.parameters["characterId"]

            if(characterId == null) {
                call.respond(HttpStatusCode.BadRequest, "No {characterId} specified")
                return@patch
            }

            if(!Utility.checkUuid(characterId)) {
                call.respond(HttpStatusCode.BadRequest, "Wrong {characterId} type")
                return@patch
            }

            try {
                val newCharacter = call.receive<CharacterItems>()
                call.respond(editCharacterItems(newCharacter, characterId))
            } catch (ex: IllegalStateException) {
                call.respond(HttpStatusCode.BadRequest)
            } catch (ex: JsonConvertException) {
                call.respond(HttpStatusCode.BadRequest)
            }
        }

        patch("/edit-character-stats/{characterId}") {
            val characterId = call.parameters["characterId"]

            if(characterId == null) {
                call.respond(HttpStatusCode.BadRequest, "No {characterId} specified")
                return@patch
            }

            if(!Utility.checkUuid(characterId)) {
                call.respond(HttpStatusCode.BadRequest, "Wrong {characterId} type")
                return@patch
            }

            try {
                val newStats = call.receive<CharacterStats>()
                call.respond(editCharacterStats(newStats, characterId))
            } catch (ex: IllegalStateException) {
                call.respond(HttpStatusCode.BadRequest)
            } catch (ex: JsonConvertException) {
                call.respond(HttpStatusCode.BadRequest)
            }
        }
    }
}