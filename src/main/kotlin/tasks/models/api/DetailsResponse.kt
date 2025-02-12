package ru.mav26.tasks.models.api

import kotlinx.serialization.Serializable

@Serializable
data class DetailsResponse(
    val linksList: List<LinksDetailsResponse>,
    val imgList: List<ImageDetailsResponse>
)
