package ru.mav26.bosses.models.db

import org.jetbrains.exposed.sql.Table
import ru.mav26.auth.models.db.UserTable

object ActiveBossTable: Table("active_boss") {
    val activeBossId = uuid("active_boss_id").autoGenerate()
    val bossId = uuid("boss_id").references(BossesDictionaryTable.bossId)
    val userLogin = varchar("user_login", 25).references(UserTable.login)
    val isCompleted = bool("is_completed").default(false)
    val currentHp = integer("current_hp")

    override val primaryKey = PrimaryKey(activeBossId)
}