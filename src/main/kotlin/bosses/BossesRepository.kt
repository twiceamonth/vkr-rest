package ru.mav26.bosses

import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update
import ru.mav26.bosses.models.api.ActiveBossResponse
import ru.mav26.bosses.models.api.BossResponse
import ru.mav26.bosses.models.db.ActiveBossTable
import ru.mav26.bosses.models.db.BossesDictionaryTable
import ru.mav26.bosses.models.dto.BossesDictionaryDto
import java.util.*

class BossesRepository {

    fun getActiveBoss(userLogin: String): ActiveBossResponse? {
        return transaction {
            val response = ActiveBossTable.select(ActiveBossTable.bossId, ActiveBossTable.currentHp)
                .where { (ActiveBossTable.userLogin eq userLogin) and (ActiveBossTable.isCompleted eq false) }

            if(response.empty()) {
                null
            } else {
                val bossId = UUID.fromString(response.first()[ActiveBossTable.bossId].toString())
                val currentHp = response.first()[ActiveBossTable.currentHp]

                val bossInfo = BossesDictionaryTable.selectAll()
                    .where(BossesDictionaryTable.bossId eq bossId).map {
                        BossesDictionaryDto(
                            bossId = it[BossesDictionaryTable.bossId],
                            bossName = it[BossesDictionaryTable.bossName],
                            criteriaType = it[BossesDictionaryTable.criteriaType],
                            criteriaValue = it[BossesDictionaryTable.criteriaValue],
                            maxHp = it[BossesDictionaryTable.maxHp],
                            resistType = it[BossesDictionaryTable.resistType],
                            resistValue = it[BossesDictionaryTable.resistValue],
                            bonusType = it[BossesDictionaryTable.bonusType],
                            bonusValue = it[BossesDictionaryTable.bonusValue],
                            baseDamage = it[BossesDictionaryTable.baseDamage],
                            imagePath = it[BossesDictionaryTable.imagePath]
                        )
                    }.first()

                ActiveBossResponse(
                    bossName = bossInfo.bossName,
                    criteriaType = bossInfo.criteriaType,
                    criteriaValue = bossInfo.criteriaValue,
                    maxHp = bossInfo.maxHp,
                    resistType = bossInfo.resistType,
                    resistValue = bossInfo.resistValue,
                    bonusType = bossInfo.bonusType,
                    bonusValue = bossInfo.bonusValue,
                    baseDamage = bossInfo.baseDamage,
                    imagePath = bossInfo.imagePath,
                    currentHp = currentHp
                )
            }
        }
    }

    fun getBossesList(): List<BossResponse> {
        return transaction {
            BossesDictionaryTable.selectAll().map {
                BossResponse(
                    bossId = it[BossesDictionaryTable.bossId].toString(),
                    criteriaType = it[BossesDictionaryTable.criteriaType],
                    criteriaValue = it[BossesDictionaryTable.criteriaValue]
                )
            }
        }
    }

    fun makeDamage(userLogin: String, tDIff: String) {
        val dmg = when(tDIff) {
            "easy" -> 1
            "medium" -> 2
            "hard" -> 3
            else -> 1
        }

        transaction {
            val currentHp = ActiveBossTable.select(ActiveBossTable.currentHp)
                .where(ActiveBossTable.userLogin eq userLogin
                        and(ActiveBossTable.isCompleted eq false)).first()[ActiveBossTable.currentHp]

            val newHp = currentHp - dmg

            ActiveBossTable.update( { (ActiveBossTable.userLogin eq userLogin
                    and(ActiveBossTable.isCompleted eq false)) } ) {
                it[ActiveBossTable.currentHp] = newHp
                if(newHp <= 0) it[ActiveBossTable.isCompleted] = true
            }
        }
    }

    fun createActiveBoss(userLogin: String, bossId: String) {
        transaction {
            val maxHp = BossesDictionaryTable.select(BossesDictionaryTable.maxHp)
                .where(BossesDictionaryTable.bossId eq UUID.fromString(bossId))
                .map { it[BossesDictionaryTable.maxHp].toString().toInt() }.first()

            ActiveBossTable.insert {
                it[ActiveBossTable.bossId] = UUID.fromString(bossId)
                it[ActiveBossTable.userLogin] = userLogin
                it[currentHp] = maxHp
            }
        }
    }

}

