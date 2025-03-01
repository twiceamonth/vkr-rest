package ru.mav26.store.models.api

import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class StoreItemsResponse(
    val storeId: String,
    val title: String,
    val imagePath: String,
    val description: String,
    val cost: Int,
    val lvlToBuy: Int,
    val isOwned: Boolean,
    val isApplied: Boolean,
    val itemId: String
)
