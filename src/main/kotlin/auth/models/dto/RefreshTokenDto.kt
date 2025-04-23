package ru.mav26.auth.models.dto

import java.time.LocalDateTime
import java.util.*

data class RefreshTokenDto(
    val tokenId: UUID,
    val userId: String,
    val tokenHash: String,
    val expiresAt: LocalDateTime
)
