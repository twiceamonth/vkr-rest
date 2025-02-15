package ru.mav26.store.models.api

import kotlinx.serialization.Serializable

@Serializable
data class InventoryResponse(
    val type: String,
    val imagePath: String
)
