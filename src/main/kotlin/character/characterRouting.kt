package ru.mav26.character

import io.ktor.http.*
import io.ktor.serialization.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import ru.mav26.Utility
import ru.mav26.character.models.api.CharacterItems
import ru.mav26.character.models.api.CharacterStats
import ru.mav26.character.models.api.CreateCharacter
import ru.mav26.getUserLogin

fun Application.characterRouting(repository: CharacterRepository) {
    routing {
        authenticate("jwt") {
            get("/get-character") {
                val userLogin = call.getUserLogin()

                if(userLogin == null) {
                    call.respond(HttpStatusCode.BadRequest, "No {userLogin} specified")
                    return@get
                }

                call.respond(repository.getCharacter(userLogin))
            }

            get("/get-all-characters") {
                val userLogin = call.getUserLogin()

                if(userLogin == null) {
                    call.respond(HttpStatusCode.BadRequest, "No {userLogin} specified")
                    return@get
                }

                call.respond(repository.getAllCharacters(userLogin))
            }

            get("/get-character-types") {
                call.respond(repository.getCharacterTypes())
            }

            get("/get-random-dialog/{mood}") {
                val mood = call.parameters["mood"]

                if (mood == null) {
                    call.respond(HttpStatusCode.BadRequest, "No {mood}")
                    return@get
                }

                call.respond(repository.getRandomDialog(mood.toInt()))
            }

            post("/create-character") {
                val userLogin = call.getUserLogin()

                if(userLogin == null) {
                    call.respond(HttpStatusCode.BadRequest, "No {userLogin} specified")
                    return@post
                }

                try {
                    val newCharacter = call.receive<CreateCharacter>()
                    call.respond(repository.createCharacter(newCharacter, userLogin))
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
                    call.respond(repository.editCharacterItems(newCharacter, characterId))
                } catch (ex: IllegalStateException) {
                    call.respond(HttpStatusCode.BadRequest, "IllegalStateException")
                } catch (ex: JsonConvertException) {
                    call.respond(HttpStatusCode.BadRequest, "JsonConvertException")
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
                    call.respond(repository.editCharacterStats(newStats, characterId))
                } catch (ex: IllegalStateException) {
                    call.respond(HttpStatusCode.BadRequest)
                } catch (ex: JsonConvertException) {
                    call.respond(HttpStatusCode.BadRequest)
                }
            }
        }
    }
}