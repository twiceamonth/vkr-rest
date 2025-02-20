package ru.mav26.events

import ru.mav26.events.models.api.ActiveEventResponse

fun getEventsList(): List<String> {
    return emptyList()
}

fun getActiveEvent() : ActiveEventResponse {
    return ActiveEventResponse(
        activeEventId = TODO(),
        eventName = TODO(),
        description = TODO(),
        eventIcon = TODO(),
        money = TODO(),
        exp = TODO(),
        criteriaType = TODO(),
        criteriaValue = TODO(),
        endDate = TODO(),
        isCompleted = TODO(),
        progress = TODO()
    )
}

fun updateEventProgress(activeEventId: String) {

}

fun createActiveEvent(eventId: String) {

}