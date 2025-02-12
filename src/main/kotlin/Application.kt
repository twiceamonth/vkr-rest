package ru.mav26

import io.ktor.server.application.*
import ru.mav26.achievements.achievementsRouting
import ru.mav26.auth.authRouting
import ru.mav26.bosses.bossesRouting
import ru.mav26.character.characterRouting
import ru.mav26.effects.effectsRouting
import ru.mav26.events.eventsRouting
import ru.mav26.store.storeRouting
import ru.mav26.tasks.tasksRouting

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    //staticResources("/content", "mycontent")

    // Plugins
    configureSerialization()
    configureDocs()
    configureDatabase()

    // Application Routes
    authRouting()
    tasksRouting()
    storeRouting()
    eventsRouting()
    effectsRouting()
    achievementsRouting()
    bossesRouting()
    characterRouting()
}
