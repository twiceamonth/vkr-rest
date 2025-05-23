package ru.mav26

import io.ktor.server.application.*
import io.ktor.server.http.content.*
import io.ktor.server.routing.*
import ru.mav26.achievements.AchievementsRepository
import ru.mav26.achievements.achievementsRouting
import ru.mav26.auth.AuthRepository
import ru.mav26.auth.authRouting
import ru.mav26.bosses.BossesRepository
import ru.mav26.bosses.bossesRouting
import ru.mav26.character.CharacterRepository
import ru.mav26.character.characterRouting
import ru.mav26.effects.EffectsRepository
import ru.mav26.effects.effectsRouting
import ru.mav26.events.EventsRepository
import ru.mav26.events.eventsRouting
import ru.mav26.statistics.StatsRepository
import ru.mav26.statistics.statsRouting
import ru.mav26.store.StoreRepository
import ru.mav26.store.storeRouting
import ru.mav26.tasks.TasksRepository
import ru.mav26.tasks.tasksRouting

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    // Repositories
    val taskRepo = TasksRepository()
    val storeRepo = StoreRepository()
    val eventRepo = EventsRepository()
    val effectsRepo = EffectsRepository()
    val bossesRepo = BossesRepository()
    val achievementsRepo = AchievementsRepository()
    val characterRepo = CharacterRepository()
    val authRepo = AuthRepository()
    val statsRepo = StatsRepository()

    // Plugins
    configureSerialization()
    configureDocs()
    configureDatabase()
    configureAuthentication()

    // Application Routes
    authRouting(authRepo)
    tasksRouting(taskRepo)
    storeRouting(storeRepo)
    eventsRouting(eventRepo)
    effectsRouting(effectsRepo)
    achievementsRouting(achievementsRepo)
    bossesRouting(bossesRepo)
    characterRouting(characterRepo)
    statsRouting(statsRepo)

    routing {
        staticResources("/static", "static")
    }
}
