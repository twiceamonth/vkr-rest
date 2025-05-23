package ru.mav26.character

import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.SqlExpressionBuilder.less
import org.jetbrains.exposed.sql.SqlExpressionBuilder.lessEq
import org.jetbrains.exposed.sql.transactions.transaction
import ru.mav26.character.models.api.*
import ru.mav26.character.models.db.*
import ru.mav26.character.models.db.CharacterTypeTable.select
import ru.mav26.character.models.dto.BonusesDto
import ru.mav26.character.models.dto.CharacterDto
import ru.mav26.character.models.dto.CharacterTypeDto
import ru.mav26.store.models.db.ItemTable
import ru.mav26.store.models.db.StoreTable
import java.time.OffsetDateTime
import java.util.*
import java.util.logging.Level
import kotlin.random.Random

class CharacterRepository {

    fun getCharacter(userLogin: String): CharacterResponse {
        return transaction {
            val character = CharacterTable.selectAll()
                .where(CharacterTable.userLogin eq userLogin and(CharacterTable.isDead eq false)).map { c ->
                CharacterDto(
                    characterId = c[CharacterTable.characterId],
                    userLogin = c[CharacterTable.userLogin],
                    characterName = c[CharacterTable.characterName],
                    gender = c[CharacterTable.gender],
                    hairId = c[CharacterTable.hairId],
                    chestplateId = c[CharacterTable.chestplateId],
                    footsId = c[CharacterTable.footsId],
                    legsId = c[CharacterTable.legsId],
                    backgroundId = c[CharacterTable.backgroundId],
                    characterType = c[CharacterTable.characterType],
                    level = c[CharacterTable.level],
                    maxHp = c[CharacterTable.maxHp],
                    currentHp = c[CharacterTable.currentHp],
                    exp = c[CharacterTable.exp],
                    coins = c[CharacterTable.coins],
                    stressCoef = c[CharacterTable.stressCoef],
                    isDead = c[CharacterTable.isDead],
                    baseDamage = c[CharacterTable.baseDamage],
                    createdAt = c[CharacterTable.createdAt],
                    deadAt = c[CharacterTable.deadAt],
                    moodLevel = c[CharacterTable.moodLevel]
                )
            }.first()
            
            val hair = ItemTable.selectAll().where(ItemTable.itemId eq character.hairId).map { 
                StoreTable.select(StoreTable.imagePath).where(StoreTable.storeId eq it[ItemTable.storeId]).map { 
                    it[StoreTable.imagePath]
                }.first()
            }.first()

            val chestplate = ItemTable.selectAll().where(ItemTable.itemId eq character.chestplateId).map {
                StoreTable.select(StoreTable.imagePath).where(StoreTable.storeId eq it[ItemTable.storeId]).map {
                    it[StoreTable.imagePath]
                }.first()
            }.first()

            val foots = ItemTable.selectAll().where(ItemTable.itemId eq character.footsId).map {
                StoreTable.select(StoreTable.imagePath).where(StoreTable.storeId eq it[ItemTable.storeId]).map {
                    it[StoreTable.imagePath]
                }.first()
            }.first()

            val legs = ItemTable.selectAll().where(ItemTable.itemId eq character.legsId).map {
                StoreTable.select(StoreTable.imagePath).where(StoreTable.storeId eq it[ItemTable.storeId]).map {
                    it[StoreTable.imagePath]
                }.first()
            }.first()

            val background = ItemTable.selectAll().where(ItemTable.itemId eq character.backgroundId).map {
                StoreTable.select(StoreTable.imagePath).where(StoreTable.storeId eq it[ItemTable.storeId]).map {
                    it[StoreTable.imagePath]
                }.first()
            }.first()

            val expToNextLvl = LevelTable.selectAll().where(LevelTable.level eq (character.level+1)).map {
                it[LevelTable.exp]
            }.first()

            val cTypeBody = select(CharacterTypeTable.imagePath)
                .where { CharacterTypeTable.characterType eq character.characterType }
                .map { it[CharacterTypeTable.imagePath] }
                .first()

            CharacterResponse(
                characterId = character.characterId.toString(),
                characterName = character.characterName,
                gender = character.gender,
                hair = hair,
                chestplate = chestplate,
                foots = foots,
                legs = legs,
                background = background,
                characterType = cTypeBody,
                level = character.level,
                maxHp = character.maxHp,
                currentHp = character.currentHp,
                exp = character.exp,
                coins = character.coins,
                stressCoef = character.stressCoef,
                moodLevel = character.moodLevel,
                createdAt = character.createdAt.toString(),
                deadAt = character.deadAt.toString(),
                expToNextLvl = expToNextLvl
            )
        }
    }

    fun getAllCharacters(userLogin: String): List<CharacterResponse> {
        return transaction {
            val characters = CharacterTable.selectAll().where(CharacterTable.userLogin eq userLogin).map { c ->
                CharacterDto(
                    characterId = c[CharacterTable.characterId],
                    userLogin = c[CharacterTable.userLogin],
                    characterName = c[CharacterTable.characterName],
                    gender = c[CharacterTable.gender],
                    hairId = c[CharacterTable.hairId],
                    chestplateId = c[CharacterTable.chestplateId],
                    footsId = c[CharacterTable.footsId],
                    legsId = c[CharacterTable.legsId],
                    backgroundId = c[CharacterTable.backgroundId],
                    characterType = c[CharacterTable.characterType],
                    level = c[CharacterTable.level],
                    maxHp = c[CharacterTable.maxHp],
                    currentHp = c[CharacterTable.currentHp],
                    exp = c[CharacterTable.exp],
                    coins = c[CharacterTable.coins],
                    stressCoef = c[CharacterTable.stressCoef],
                    isDead = c[CharacterTable.isDead],
                    baseDamage = c[CharacterTable.baseDamage],
                    createdAt = c[CharacterTable.createdAt],
                    deadAt = c[CharacterTable.deadAt],
                    moodLevel = c[CharacterTable.moodLevel]
                )
            }

            val response = mutableListOf<CharacterResponse>()
            characters.forEach { c ->
                val hair = ItemTable.selectAll().where(ItemTable.itemId eq c.hairId).map {
                    StoreTable.select(StoreTable.imagePath).where(StoreTable.storeId eq it[ItemTable.storeId]).map {
                        it[StoreTable.imagePath]
                    }.first()
                }.first()

                val chestplate = ItemTable.selectAll().where(ItemTable.itemId eq c.chestplateId).map {
                    StoreTable.select(StoreTable.imagePath).where(StoreTable.storeId eq it[ItemTable.storeId]).map {
                        it[StoreTable.imagePath]
                    }.first()
                }.first()

                val foots = ItemTable.selectAll().where(ItemTable.itemId eq c.footsId).map {
                    StoreTable.select(StoreTable.imagePath).where(StoreTable.storeId eq it[ItemTable.storeId]).map {
                        it[StoreTable.imagePath]
                    }.first()
                }.first()

                val legs = ItemTable.selectAll().where(ItemTable.itemId eq c.legsId).map {
                    StoreTable.select(StoreTable.imagePath).where(StoreTable.storeId eq it[ItemTable.storeId]).map {
                        it[StoreTable.imagePath]
                    }.first()
                }.first()

                val background = ItemTable.selectAll().where(ItemTable.itemId eq c.backgroundId).map {
                    StoreTable.select(StoreTable.imagePath).where(StoreTable.storeId eq it[ItemTable.storeId]).map {
                        it[StoreTable.imagePath]
                    }.first()
                }.first()

                val cTypeBody = select(CharacterTypeTable.imagePath)
                    .where { CharacterTypeTable.characterType eq c.characterType }
                    .map { it[CharacterTypeTable.imagePath] }
                    .first()

                response.add(
                    CharacterResponse(
                        characterId = c.characterId.toString(),
                        characterName = c.characterName,
                        gender = c.gender,
                        hair = hair,
                        chestplate = chestplate,
                        foots = foots,
                        legs = legs,
                        background = background,
                        characterType = cTypeBody,
                        level = c.level,
                        maxHp = c.maxHp,
                        currentHp = c.currentHp,
                        exp = c.exp,
                        coins = c.coins,
                        stressCoef = c.stressCoef,
                        moodLevel = c.moodLevel,
                        createdAt = c.createdAt.toString(),
                        deadAt = c.deadAt.toString(),
                        expToNextLvl = -1
                    )
                )
            }

            response
        }
    }

    fun getCharacterTypes(): List<CharacterTypesResponse> {
        return transaction { 
            val types = CharacterTypeTable.selectAll().map { 
                CharacterTypeDto(
                    characterType = it[CharacterTypeTable.characterType],
                    imagePath = it[CharacterTypeTable.imagePath],
                    bonusId = it[CharacterTypeTable.bonusId]
                )
            }
            
            val bonuses = mutableListOf<BonusesDto>()
            types.forEach { t ->
                BonusesTable.selectAll().where(BonusesTable.bonusId eq t.bonusId).map { 
                    bonuses.add(
                        BonusesDto(
                            bonusId = it[BonusesTable.bonusId],
                            bonusType = it[BonusesTable.bonusType],
                            description = it[BonusesTable.description],
                            effect = it[BonusesTable.effect],
                            multiplier = it[BonusesTable.multiplier]
                        )
                    )
                }
            }
            
            val response = mutableListOf<CharacterTypesResponse>()
            types.forEach { t ->
                response.add(
                    CharacterTypesResponse(
                        characterType = t.characterType,
                        imagePath = t.imagePath,
                        bonusType = bonuses.filter { it.bonusId == t.bonusId }.map { it.bonusType }.first(),
                        description = bonuses.filter { it.bonusId == t.bonusId }.map { it.description }.first(),
                        effect = bonuses.filter { it.bonusId == t.bonusId }.map { it.effect }.first(),
                        multiplier = bonuses.filter { it.bonusId == t.bonusId }.map { it.multiplier }.first()
                    )
                )
            }
                
            response
        }
    }

    fun getRandomDialog(mood: Int) : String {
        return transaction {
            val dialogs = DialogsTable.select(DialogsTable.dialogText).where(DialogsTable.isForMood less mood).map {
                it[DialogsTable.dialogText]
            }

            val randomIndex = Random.nextInt(0, dialogs.size)

            dialogs[randomIndex]
        }
    }

    fun createCharacter(newCharacter: CreateCharacter, userLogin: String) {
        transaction {
            CharacterTable.insert {
                it[CharacterTable.userLogin] = userLogin
                it[CharacterTable.characterName] = newCharacter.characterName
                it[CharacterTable.gender] = newCharacter.gender

                it[CharacterTable.hairId] = ItemTable.insert {
                    it[ItemTable.storeId] = UUID.fromString(newCharacter.hairId)
                    it[ItemTable.userLogin] = userLogin
                }[ItemTable.itemId]

                it[CharacterTable.chestplateId] = ItemTable.insert {
                    it[ItemTable.storeId] = UUID.fromString(newCharacter.chestplateId)
                    it[ItemTable.userLogin] = userLogin
                }[ItemTable.itemId]

                it[CharacterTable.footsId] = ItemTable.insert {
                    it[ItemTable.storeId] = UUID.fromString(newCharacter.footsId)
                    it[ItemTable.userLogin] = userLogin
                }[ItemTable.itemId]

                it[CharacterTable.legsId] = ItemTable.insert {
                    it[ItemTable.storeId] = UUID.fromString(newCharacter.legsId)
                    it[ItemTable.userLogin] = userLogin
                }[ItemTable.itemId]

                it[CharacterTable.backgroundId] = ItemTable.insert {
                    it[ItemTable.storeId] = UUID.fromString(newCharacter.backgroundId)
                    it[ItemTable.userLogin] = userLogin
                }[ItemTable.itemId]

                it[CharacterTable.characterType] = newCharacter.characterType
            }


        }
    }

    fun editCharacterItems(newCharacter: CharacterItems, characterId: String) {
        transaction {
            CharacterTable.update({ CharacterTable.characterId eq UUID.fromString(characterId) }) { c ->
                newCharacter.hairId?.let { c[CharacterTable.hairId] = UUID.fromString(it) }
                newCharacter.chestplateId?.let { c[CharacterTable.chestplateId] = UUID.fromString(it) }
                newCharacter.footsId?.let { c[CharacterTable.footsId] = UUID.fromString(it) }
                newCharacter.legsId?.let { c[CharacterTable.legsId] = UUID.fromString(it) }
                newCharacter.backgroundId?.let { c[CharacterTable.backgroundId] = UUID.fromString(it) }
            }
        }
    }

    fun editCharacterStats(newStats: CharacterStats, characterId: String) {
        transaction {
            CharacterTable.update({ CharacterTable.characterId eq UUID.fromString(characterId) }) { c ->
                newStats.level?.let { c[CharacterTable.level] = it }
                newStats.maxHp?.let { c[CharacterTable.maxHp] = it }
                newStats.currentHp?.let { c[CharacterTable.currentHp] = it }
                newStats.exp?.let { c[CharacterTable.exp] = it }
                newStats.coins?.let { c[CharacterTable.coins] = it }
                newStats.stressCoef?.let { c[CharacterTable.stressCoef] = it }
                newStats.isDead?.let { c[CharacterTable.isDead] = it }
                newStats.baseDamage?.let { c[CharacterTable.baseDamage] = it }
                newStats.deadAt?.let { c[CharacterTable.deadAt] = OffsetDateTime.parse(it) }
                newStats.moodLevel?.let { c[CharacterTable.moodLevel] = it }
            }
        }
    }

}