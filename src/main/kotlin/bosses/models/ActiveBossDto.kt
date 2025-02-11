package ru.mav26.bosses.models

import java.util.UUID

data class ActiveBossDto(
    val activeBossId: UUID,
    val bossId: UUID,
    val userLogin: String,
    val isCompleted: Boolean,
    val currentHp: Int
)
