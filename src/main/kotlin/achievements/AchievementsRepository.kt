package ru.mav26.achievements

import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update
import ru.mav26.achievements.models.api.AchievementProgressResponse
import ru.mav26.achievements.models.api.AchievementResponse
import ru.mav26.achievements.models.db.AchievementsDictionaryTable
import ru.mav26.achievements.models.db.AchievementsProgressTable
import java.time.LocalDate
import java.util.*

class AchievementsRepository {

    fun getAchievementsList(): List<AchievementResponse> {
        return transaction {
            AchievementsDictionaryTable.selectAll().map {
                AchievementResponse(
                    achievementId = it[AchievementsDictionaryTable.achievementId].toString(),
                    title = it[AchievementsDictionaryTable.title],
                    description = it[AchievementsDictionaryTable.description],
                    resetOnEvent = it[AchievementsDictionaryTable.resetOnEvent],
                    resentEventType = it[AchievementsDictionaryTable.resetEventType],
                    criteriaType = it[AchievementsDictionaryTable.criteriaType],
                    criteriaValue = it[AchievementsDictionaryTable.criteriaValue],
                    imagePath = it[AchievementsDictionaryTable.imagePath],
                )
            }
        }
    }

    fun getAchievementsProgress(userLogin: String): List<AchievementProgressResponse> {
        return transaction {
            AchievementsProgressTable.selectAll().where(AchievementsProgressTable.userLogin eq userLogin).map {
                val achievement = AchievementsDictionaryTable.selectAll().where(
                    AchievementsDictionaryTable.achievementId eq it[AchievementsProgressTable.achievementId]
                ).first()

                AchievementProgressResponse(
                    userLogin = userLogin,
                    achievementId = it[AchievementsProgressTable.achievementId].toString(),
                    progressId = it[AchievementsProgressTable.progressId].toString(),
                    title = achievement[AchievementsDictionaryTable.title],
                    description = achievement[AchievementsDictionaryTable.description],
                    resetOnEvent = achievement[AchievementsDictionaryTable.resetOnEvent],
                    resentEventType = achievement[AchievementsDictionaryTable.resetEventType],
                    criteriaType = achievement[AchievementsDictionaryTable.criteriaType],
                    criteriaValue = achievement[AchievementsDictionaryTable.criteriaValue],
                    progressValue = it[AchievementsProgressTable.progressValue],
                    isCompleted = it[AchievementsProgressTable.isCompleted],
                    completeDate = it[AchievementsProgressTable.completeDate]?.toString(),
                    imagePath = achievement[AchievementsDictionaryTable.imagePath],
                )
            }
        }
    }

    fun startAchievementProgress(userLogin: String, achievementId: String) {
        transaction {
            AchievementsProgressTable.insert {
                it[AchievementsProgressTable.userLogin] = userLogin
                it[AchievementsProgressTable.achievementId] = UUID.fromString(achievementId)
            }
        }
    }

    fun updateAchievementProgress(progressId: String, userLogin: String) {
        transaction {
            val aid = AchievementsProgressTable.select(AchievementsProgressTable.achievementId)
                .where(AchievementsProgressTable.progressId eq UUID.fromString(progressId))
                .first()[AchievementsProgressTable.achievementId]

            val curProgress = AchievementsProgressTable.select(AchievementsProgressTable.progressValue)
                .where(AchievementsProgressTable.progressId eq UUID.fromString(progressId))
                .first()[AchievementsProgressTable.progressValue]

            val cValue = AchievementsDictionaryTable.select(AchievementsDictionaryTable.criteriaValue)
                .where(AchievementsDictionaryTable.achievementId eq aid)
                .first()[AchievementsDictionaryTable.criteriaValue]

            AchievementsProgressTable.update({AchievementsProgressTable.progressId eq UUID.fromString(progressId)}) {
                it[AchievementsProgressTable.progressValue] = if(curProgress + 1 < cValue) curProgress + 1 else curProgress
                if(curProgress + 1 == cValue) {
                    it[AchievementsProgressTable.isCompleted] = true
                    it[AchievementsProgressTable.completeDate] = LocalDate.now()
                }
            }
        }
    }

    fun resetProgress(progressId: String, userLogin: String) {
        transaction {
            AchievementsProgressTable.update({AchievementsProgressTable.progressId eq UUID.fromString(progressId) and(
                    AchievementsProgressTable.userLogin eq userLogin)}) {
                it[AchievementsProgressTable.progressValue] = 0
                it[AchievementsProgressTable.isCompleted] = false
                it[AchievementsProgressTable.completeDate] = null
            }
        }
    }

}