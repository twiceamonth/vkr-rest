package ru.mav26.auth.models.dto

import java.util.Date

data class UserDto(
    val login: String,
    val password: String,
    val registeredAt: Date
)