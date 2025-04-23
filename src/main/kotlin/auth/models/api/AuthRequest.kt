package ru.mav26.auth.models.api

import kotlinx.serialization.Serializable

@Serializable
data class AuthRequest(
    val userLogin: String,
    val password: String
)
