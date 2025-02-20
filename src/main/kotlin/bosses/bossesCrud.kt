package ru.mav26.bosses

import ru.mav26.bosses.models.api.ActiveBossResponse
import ru.mav26.bosses.models.api.BossResponse

fun getActiveBoss(userLogin: String): ActiveBossResponse {
    return ActiveBossResponse(
        bossName = TODO(),
        criteriaType = TODO(),
        criteriaValue = TODO(),
        maxHp = TODO(),
        resistType = TODO(),
        resistValue = TODO(),
        bonusType = TODO(),
        bonusValue = TODO(),
        baseDamage = TODO(),
        imagePath = TODO(),
        currentHp = TODO()
    )
}

fun getBossesList(): List<BossResponse> {
    return emptyList()
}

fun createActiveBoss(userLogin: String, bossId: String) {

}