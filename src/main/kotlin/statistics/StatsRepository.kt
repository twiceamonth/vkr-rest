package ru.mav26.statistics

import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.SqlExpressionBuilder.isNotNull
import org.jetbrains.exposed.sql.javatime.JavaLocalDateColumnType
import org.jetbrains.exposed.sql.javatime.month
import org.jetbrains.exposed.sql.javatime.year
import org.jetbrains.exposed.sql.transactions.transaction
import ru.mav26.statistics.models.dto.AvgTimeStats
import ru.mav26.statistics.models.dto.DailyStats
import ru.mav26.statistics.models.dto.SummaryStats
import ru.mav26.tasks.models.db.TaskTable
import ru.mav26.statistics.models.dto.DailyCount
import java.time.Duration
import java.time.LocalDate

class StatsRepository {
    fun totalByDifficulty(userLogin: String): SummaryStats = transaction {
        val total = TaskTable
            .selectAll().where { (TaskTable.finishedAt.isNotNull()) and (TaskTable.userLogin eq userLogin) }
            .count()
            .toInt()

        val light = TaskTable
            .selectAll()
            .where { (TaskTable.difficulty eq "low") and (TaskTable.finishedAt.isNotNull()) and (TaskTable.userLogin eq userLogin) }
            .count()
            .toInt()

        val medium = TaskTable
            .selectAll()
            .where { (TaskTable.difficulty eq "medium") and (TaskTable.finishedAt.isNotNull()) and (TaskTable.userLogin eq userLogin) }
            .count()
            .toInt()

        val hard = TaskTable
            .selectAll()
            .where { (TaskTable.difficulty eq "high") and (TaskTable.finishedAt.isNotNull()) and (TaskTable.userLogin eq userLogin) }
            .count()
            .toInt()

        SummaryStats(total, light, medium, hard)
    }

    private fun avgDaysFor(diff: String, userLogin: String): Double = transaction {
        val avgSeconds: Double = exec(
            """
        SELECT EXTRACT(EPOCH FROM AVG(finished_at - created_at))
        FROM task
        WHERE difficulty = '$diff' AND finished_at IS NOT NULL AND user_login = '$userLogin'
        """.trimIndent()
        ) { rs: java.sql.ResultSet ->
            if (rs.next()) rs.getDouble(1) else 0.0
        } ?: 0.0

        avgSeconds / 86400.0
    }

    fun avgTimeByDifficulty(userLogin: String): AvgTimeStats = transaction {
        AvgTimeStats(
            lightDays  = avgDaysFor("low", userLogin),
            mediumDays = avgDaysFor("medium", userLogin),
            hardDays   = avgDaysFor("high", userLogin)
        )
    }

    fun dailyCounts(userLogin: String, year: Int, month: Int): DailyStats = transaction {
        val rows = TaskTable
            .select(
                TaskTable.finishedAt!!.castTo<LocalDate>(JavaLocalDateColumnType()),
                TaskTable.difficulty,
                TaskTable.taskId.count()
            )
            .where { (TaskTable.finishedAt.isNotNull()) and (TaskTable.userLogin eq userLogin) }
            .andWhere {
                val dt = TaskTable.finishedAt!!.castTo<LocalDate>(JavaLocalDateColumnType())
                (dt.year() eq year) and (dt.month() eq month)
            }
            .groupBy(TaskTable.finishedAt!!.castTo<LocalDate>(JavaLocalDateColumnType()), TaskTable.difficulty)
            .map {
                Triple(
                    it[TaskTable.finishedAt.castTo<LocalDate>(JavaLocalDateColumnType())],
                    it[TaskTable.difficulty],
                    it[TaskTable.taskId.count()]
                )
            }

        val byDate = mutableMapOf<LocalDate, Triple<Int, Int, Int>>()
        for ((date, diff, cnt) in rows) {
            val prev = byDate.getOrDefault(date, Triple(0, 0, 0))
            val updated = when (diff) {
                "light"  -> prev.copy(first = cnt.toInt())
                "medium" -> prev.copy(second = cnt.toInt())
                "hard"   -> prev.copy(third = cnt.toInt())
                else     -> prev
            }
            byDate[date] = updated
        }
        DailyStats(
            byDate.entries
                .sortedBy { it.key }
                .map { (d, triple) ->
                    DailyCount(d, triple.first, triple.second, triple.third)
                }
        )
    }

}