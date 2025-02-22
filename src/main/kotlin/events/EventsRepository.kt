package ru.mav26.events

import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update
import ru.mav26.events.models.api.ActiveEventResponse
import ru.mav26.events.models.db.ActiveEventTable
import ru.mav26.events.models.db.EventDictionaryTable
import ru.mav26.events.models.db.UserEventProgressTable
import ru.mav26.events.models.dto.ActiveEventDto
import ru.mav26.events.models.dto.EventDictionaryDto
import java.time.LocalDate
import java.util.*
import kotlin.random.Random

class EventsRepository {

    fun getEventsList(): List<String> {
        return transaction {
            EventDictionaryTable.select(EventDictionaryTable.eventId).map {
                it.toString()
            }
        }
    }

    fun getActiveEvent(userLogin: String) : ActiveEventResponse {
        return transaction {
            val activeEvent = ActiveEventTable.selectAll().where(ActiveEventTable.isCompleted eq false).map {
                ActiveEventDto(
                    activeEventId = it[ActiveEventTable.activeEventId],
                    eventId = it[ActiveEventTable.eventId],
                    startDate = it[ActiveEventTable.startDate],
                    endDate = it[ActiveEventTable.endDate],
                    isCompleted = it[ActiveEventTable.isCompleted]
                )
            }.first()

            val eventInfo = EventDictionaryTable.selectAll()
                .where(EventDictionaryTable.eventId eq activeEvent.eventId).map {
                    EventDictionaryDto(
                        eventId = it[EventDictionaryTable.eventId],
                        eventName = it[EventDictionaryTable.eventName],
                        description = it[EventDictionaryTable.description],
                        eventIcon = it[EventDictionaryTable.eventIcon],
                        money = it[EventDictionaryTable.money],
                        exp = it[EventDictionaryTable.exp],
                        criteriaType = it[EventDictionaryTable.criteriaType],
                        criteriaValue = it[EventDictionaryTable.criteriaValue]
                    )
            }.first()

            val progress = UserEventProgressTable.select(UserEventProgressTable.progress)
                .where(UserEventProgressTable.activeEventId eq activeEvent.activeEventId
                        and(UserEventProgressTable.userLogin eq userLogin)).map { it.toString().toInt() }.first()

            ActiveEventResponse(
                activeEventId = activeEvent.activeEventId.toString(),
                eventName = eventInfo.eventName,
                description = eventInfo.description,
                eventIcon = eventInfo.eventIcon,
                money = eventInfo.money,
                exp = eventInfo.exp,
                criteriaType = eventInfo.criteriaType,
                criteriaValue = eventInfo.criteriaValue,
                endDate = activeEvent.endDate.toString(),
                isCompleted = activeEvent.isCompleted,
                progress = progress
            )
        }
    }

    fun updateEventProgress(activeEventId: String, userLogin: String, type: String) {
        transaction {
            val currProgress = UserEventProgressTable.select(UserEventProgressTable.progress)
                .where((UserEventProgressTable.activeEventId eq UUID.fromString(activeEventId))
                        and (UserEventProgressTable.userLogin eq userLogin)).map { it.toString().toInt() }.first()

            val eventId = ActiveEventTable.select(ActiveEventTable.eventId)
                .where((ActiveEventTable.activeEventId eq UUID.fromString(activeEventId))).map { it.toString() }.first()

            val cValue = EventDictionaryTable.select(EventDictionaryTable.criteriaValue)
                .where(EventDictionaryTable.eventId eq UUID.fromString(eventId)).map {
                    it.toString().toInt() }.first()

            val newProgress = if(type == "p") currProgress + 1 else currProgress - 1

            UserEventProgressTable.update({ (UserEventProgressTable.activeEventId eq UUID.fromString(activeEventId)
                    and (UserEventProgressTable.userLogin eq userLogin)) }) {
                it[UserEventProgressTable.progress] =  newProgress

                if(newProgress == cValue && type == "p") {
                    it[UserEventProgressTable.isCompleted] = true
                } else {
                    it[UserEventProgressTable.isCompleted] = false
                }
            }
        }
    }

    fun createActiveEvent(eventId: String) {
        val today = LocalDate.now()
        val maxDays = 14

        val randomDays = Random.nextInt(0, maxDays + 1)
        val randomStartDate = today.plusDays(randomDays.toLong())
        val endDate = randomStartDate.plusDays(7)

        ActiveEventTable.insert {
            it[ActiveEventTable.eventId] = UUID.fromString(eventId)
            it[ActiveEventTable.startDate] = randomStartDate
            it[ActiveEventTable.endDate] = endDate
        }
    }

}

