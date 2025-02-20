package ru.mav26.auth

import ru.mav26.auth.models.api.CreateAuth
import ru.mav26.auth.models.api.TokensResponse

fun login(user: CreateAuth): TokensResponse {
    return TokensResponse(
        accessToken = TODO(),
        refreshToken = TODO()
    )
}

fun register(user: CreateAuth): TokensResponse {
    return TokensResponse(
        accessToken = TODO(),
        refreshToken = TODO()
    )
}