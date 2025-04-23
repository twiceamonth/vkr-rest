package ru.mav26.auth.models.dto

import java.time.LocalDate

data class UserDto(
    val login: String,
    val password: String,
    val registeredAt: LocalDate
)