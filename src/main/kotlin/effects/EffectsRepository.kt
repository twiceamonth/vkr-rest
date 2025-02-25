package ru.mav26.effects

import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import ru.mav26.effects.models.api.ActiveEffectResponse
import ru.mav26.effects.models.db.ActiveEffectEventTable
import ru.mav26.effects.models.db.EffectEventDictionaryTable
import ru.mav26.effects.models.dto.ActiveEffectEventDto
import ru.mav26.effects.models.dto.EffectEventDictionaryDto
import java.time.LocalDate
import java.util.*
import kotlin.random.Random

class EffectsRepository {

    fun getActiveEffect(): ActiveEffectResponse? {
        return transaction {
            val activeEffect = ActiveEffectEventTable.selectAll()
                .where({ ActiveEffectEventTable.isCompleted eq false }).map {
                    ActiveEffectEventDto(
                        activeEffectId = it[ActiveEffectEventTable.activeEffectId],
                        effectEventId = it[ActiveEffectEventTable.effectEventId],
                        startDate = it[ActiveEffectEventTable.startDate],
                        endDate = it[ActiveEffectEventTable.endDate],
                        isCompleted = it[ActiveEffectEventTable.isCompleted]
                    )
                }.firstOrNull()

            if(activeEffect == null) {
                null
            } else {
                val effectInfo = EffectEventDictionaryTable.selectAll()
                    .where({ EffectEventDictionaryTable.effectEventId eq activeEffect.effectEventId }).map {
                        EffectEventDictionaryDto(
                            effectEventId = it[EffectEventDictionaryTable.effectEventId],
                            effectName = it[EffectEventDictionaryTable.effectName],
                            description = it[EffectEventDictionaryTable.description],
                            effectIcon = it[EffectEventDictionaryTable.effectIcon],
                            chance = it[EffectEventDictionaryTable.chance],
                            criteriaType = it[EffectEventDictionaryTable.criteriaType],
                            criteriaValue = it[EffectEventDictionaryTable.criteriaValue]
                        )
                    }.first()

                ActiveEffectResponse(
                    activeEffectId = activeEffect.activeEffectId.toString(),
                    endDate = activeEffect.endDate.toString(),
                    isCompleted = activeEffect.isCompleted,
                    effectName = effectInfo.effectName,
                    description = effectInfo.description,
                    effectIcon = effectInfo.effectIcon,
                    criteriaType = effectInfo.criteriaType,
                    criteriaValue = effectInfo.criteriaValue
                )
            }
        }
    }

    fun getEffectsList(): List<String> {
        return transaction {
            EffectEventDictionaryTable.select(EffectEventDictionaryTable.effectEventId).map {
                it[EffectEventDictionaryTable.effectEventId].toString()
            }
        }
    }

    fun createActiveEffect(effectId: String) {
        val today = LocalDate.now()
        val maxDays = 30

        val randomDays = Random.nextInt(0, maxDays + 1)
        val randomStartDate = today.plusDays(randomDays.toLong())
        val endDate = randomStartDate.plusDays(7)

        transaction {
            ActiveEffectEventTable.insert {
                it[effectEventId] = UUID.fromString(effectId)
                it[startDate] = randomStartDate
                it[ActiveEffectEventTable.endDate] = endDate
            }
        }
    }

}