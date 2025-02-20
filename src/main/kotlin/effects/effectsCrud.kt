package ru.mav26.effects

import ru.mav26.effects.models.api.ActiveEffectResponse

fun getActiveEffect(): ActiveEffectResponse {
    return ActiveEffectResponse(
        activeEffectId = TODO(),
        endDate = TODO(),
        isCompleted = TODO(),
        effectName = TODO(),
        description = TODO(),
        effectIcon = TODO(),
        criteriaType = TODO(),
        criteriaValue = TODO()
    )
}

fun getEffectsList(): List<String> {
    return emptyList()
}

fun createActiveEffect(effectId: String) {

}