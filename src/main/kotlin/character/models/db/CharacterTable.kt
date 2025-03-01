package ru.mav26.character.models.db

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.timestampWithTimeZone
import ru.mav26.auth.models.db.UserTable
import ru.mav26.store.models.db.ItemTable
import java.time.OffsetDateTime

object CharacterTable: Table("character") {
    val characterId = uuid("character_id").autoGenerate()
    val userLogin = varchar("user_login", 25).references(UserTable.login)
    val characterName = varchar("character_name", 25)
    val gender = bool("gender")
    val hairId = uuid("hair_id").references(ItemTable.itemId)
    val chestplateId = uuid("chestplate_id").references(ItemTable.itemId)
    val footsId = uuid("foots_id").references(ItemTable.itemId)
    val legsId = uuid("legs_id").references(ItemTable.itemId)
    val backgroundId = uuid("background_id").references(ItemTable.itemId)
    val characterType = varchar("character_type", 25).references(CharacterTypeTable.characterType)
    val level = integer("level").references(LevelTable.level).default(0)
    val maxHp = integer("max_hp").default(50)
    val currentHp = integer("current_hp").default(50)
    val exp = integer("exp").default(0)
    val coins = integer("coins").default(0)
    val stressCoef = integer("stress_coef").default(0)
    val isDead = bool("is_dead").default(false)
    val baseDamage = integer("base_damage").default(1)
    val createdAt = timestampWithTimeZone("created_at").default(OffsetDateTime.now())
    val deadAt = timestampWithTimeZone("dead_at").nullable()
    val moodLevel = integer("mood_level").default(100)

    override val primaryKey = PrimaryKey(characterId)
}