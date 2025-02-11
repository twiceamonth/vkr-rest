package ru.mav26.store.models

import java.util.UUID

data class StoreDto(
    val storeId: UUID,
    val itemId: UUID,
    val characterId: UUID,
    val cost: Int,
    val lvlToBuy: Int,
    val isOwned: Boolean
)
