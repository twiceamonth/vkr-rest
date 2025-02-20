package ru.mav26.character

import ru.mav26.character.models.api.*

fun getCharacter(userLogin: String): CharacterResponse {
    return CharacterResponse(
        characterId = TODO(),
        characterName = TODO(),
        gender = TODO(),
        hair = TODO(),
        chestplate = TODO(),
        foots = TODO(),
        legs = TODO(),
        background = TODO(),
        characterType = TODO(),
        level = TODO(),
        maxHp = TODO(),
        currentHp = TODO(),
        exp = TODO(),
        coins = TODO(),
        stressCoef = TODO(),
        createdAt = TODO(),
        deadAt = TODO()
    )
}

fun getAllCharacters(userLogin: String): List<CharacterResponse> {
    return emptyList()
}

fun getCharacterTypes(): List<CharacterTypesResponse> {
    return emptyList()
}

fun getRandomDialog() : String {
    return ""
}

fun createCharacter(newCharacter: CreateCharacter, userLogin: String) {

}

fun editCharacterItems(newCharacter: CharacterItems, characterId: String) {

}

fun editCharacterStats(newStats: CharacterStats, characterId: String) {

}