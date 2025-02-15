package ru.mav26.character.models.api

import kotlinx.serialization.Serializable

@Serializable
data class CreateCharacter(
    val characterName: String,
    val gender: Boolean,
    val hairId: String,
    val chestplateId: String,
    val footsId: String,
    val legsId: String,
    val backgroundId: String,
    val characterType: String
)
