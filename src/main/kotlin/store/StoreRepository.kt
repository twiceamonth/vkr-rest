package ru.mav26.store

import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import ru.mav26.store.models.api.InventoryResponse
import ru.mav26.store.models.api.StoreItemsResponse
import ru.mav26.store.models.db.ItemTable
import ru.mav26.store.models.db.StoreTable
import java.util.*

class StoreRepository {

    fun getItemsList(type: String, characterId: String): List<StoreItemsResponse> {
        return transaction {
            StoreTable.selectAll().where(StoreTable.type eq type).map { si ->
                val itemId = ItemTable.select(ItemTable.itemId)
                    .where(StoreTable.storeId eq ItemTable.storeId).map {
                        it.toString()
                    }.first()

                val isOwned = ItemTable.select(ItemTable.itemId)
                    .where(ItemTable.characterId eq UUID.fromString(characterId)).map {
                        if (it != null) true else false
                    }.first()

                val isApplied = false // TODO: ДОоделать запрос когда персонажа сделаю

                StoreItemsResponse(
                    itemId = itemId,
                    title = si[StoreTable.title],
                    imagePath = si[StoreTable.imagePath],
                    description = si[StoreTable.description],
                    cost = si[StoreTable.cost],
                    lvlToBuy = si[StoreTable.lvlToBuy],
                    isOwned = isOwned,
                    isApplied = isApplied
                )
            }
        }
    }

    fun getInventory(characterId: String): List<InventoryResponse> {
        return transaction {
            ItemTable.selectAll().where(ItemTable.characterId eq UUID.fromString(characterId)).map { i ->
                InventoryResponse(
                    type = StoreTable.select(StoreTable.type).where(StoreTable.storeId eq i[ItemTable.storeId])
                        .map { it.toString() }.first(),
                    imagePath = StoreTable.select(StoreTable.imagePath)
                        .where(StoreTable.storeId eq i[ItemTable.storeId])
                        .map { it.toString() }.first()
                )
            }
        }
    }

    fun healButton() {
        // TODO: Доделать когда персонажа сделаю
    }

    fun buyItem(characterId: String, storeId: String) {
        transaction {
            ItemTable.insert {
                it[this.storeId] = UUID.fromString(storeId)
                it[ItemTable.characterId] = UUID.fromString(characterId)
            }
        }
    }

}

