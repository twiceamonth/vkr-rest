package ru.mav26.character

import ru.mav26.character.models.api.*

fun getCharacter(userLogin: String): CharacterResponse {

}

fun getAllCharacters(userLogin: String): List<CharacterResponse> {

}

fun getCharacterTypes(): List<CharacterTypesResponse> {

}

fun getRandomDialog() : String {

}

fun createCharacter(newCharacter: CreateCharacter, userLogin: String) {

}

fun editCharacterItems(newCharacter: CharacterItems, characterId: String) {

}

fun editCharacterStats(newStats: CharacterStats, characterId: String) {

}