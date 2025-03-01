package ru.mav26.store

import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import ru.mav26.store.models.api.InventoryResponse
import ru.mav26.store.models.api.StoreItemsResponse
import ru.mav26.store.models.db.ItemTable
import ru.mav26.store.models.db.StoreTable
import java.util.*

class StoreRepository {

    fun getItemsList(type: String, userLogin: String): List<StoreItemsResponse> {
        return transaction {
            StoreTable.selectAll().where(StoreTable.type eq type).map { si ->
                val itemId = ItemTable.select(ItemTable.itemId)
                    .where( ItemTable.storeId eq si[StoreTable.storeId])
                    .map { it[ItemTable.itemId] }
                    .firstOrNull()

                val isOwned = if(itemId != null){
                    ItemTable.selectAll()
                        .where (
                            (ItemTable.userLogin eq userLogin) and
                                    (ItemTable.itemId eq itemId)
                        ).any()
                } else { false }

                val isApplied = false // TODO: ДОоделать запрос когда персонажа сделаю

                StoreItemsResponse(
                    itemId = itemId.toString(),
                    storeId = si[StoreTable.storeId].toString(),
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

    fun getInventory(userLogin: String): List<InventoryResponse> {
        return transaction {
            ItemTable.selectAll().where(ItemTable.userLogin eq userLogin).map { i ->
                InventoryResponse(
                    type = StoreTable.select(StoreTable.type).where(StoreTable.storeId eq i[ItemTable.storeId])
                        .map { it[StoreTable.type] }.first(),
                    imagePath = StoreTable.select(StoreTable.imagePath)
                        .where(StoreTable.storeId eq i[ItemTable.storeId])
                        .map { it[StoreTable.imagePath] }.first()
                )
            }
        }
    }

    fun healButton() {
        // TODO: Доделать когда персонажа сделаю
    }

    fun buyItem(userLogin: String, storeId: String) {
        transaction {
            ItemTable.insert {
                it[this.storeId] = UUID.fromString(storeId)
                it[ItemTable.userLogin] = userLogin
            }
        }
    }

}

