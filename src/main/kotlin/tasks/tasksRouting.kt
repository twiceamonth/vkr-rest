package ru.mav26.tasks

import io.ktor.http.*
import io.ktor.serialization.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import ru.mav26.Utility
import ru.mav26.tasks.models.api.HabitCreate
import ru.mav26.tasks.models.api.TaskCreate

fun Application.tasksRouting() {
    routing {
        get("/tasks-list") {
            call.respond(getTasksList())
        }

        get("/habit-list") {
            call.respond(getHabitList())
        }

        get("/task-details/{taskId}") {
            val taskId = call.parameters["taskId"]

            if(taskId == null) {
                call.respond(HttpStatusCode.BadRequest,"No {taskId} parameter")
                return@get
            }

            if(!Utility.checkUuid(taskId)) {
                call.respond(HttpStatusCode.BadRequest, "Wrong type of {taskId}")
                return@get
            }

            call.respond(getTasksDetails(taskId))
        }

        get("/habit-details/{habitId}") {
            val habitId = call.parameters["habitId"]

            if(habitId == null) {
                call.respond(HttpStatusCode.BadRequest, "No {habitId} parameter")
                return@get
            }

            if(!Utility.checkUuid(habitId)) {
                call.respond(HttpStatusCode.BadRequest, "Wrong type of {habitId}")
                return@get
            }

            call.respond(getHabitDetails(habitId))
        }

        post("/new-task") {
            try {
                val task = call.receive<TaskCreate>()
                call.respond(createTask(task))
            } catch (ex: IllegalStateException) {
                call.respond(HttpStatusCode.BadRequest)
            } catch (ex: JsonConvertException) {
                call.respond(HttpStatusCode.BadRequest)
            }
        }

        post("/new-habit") {
            try {
                val habit = call.receive<HabitCreate>()
                call.respond(createHabit(habit))
            } catch (ex: IllegalStateException) {
                call.respond(HttpStatusCode.BadRequest)
            } catch (ex: JsonConvertException) {
                call.respond(HttpStatusCode.BadRequest)
            }
        }

        patch("/edit-task/{taskId}") {
            val taskId = call.parameters["taskId"]

            if(taskId == null) {
                call.respond(HttpStatusCode.BadRequest,"No {taskId} parameter")
                return@patch
            }

            if(!Utility.checkUuid(taskId)) {
                call.respond(HttpStatusCode.BadRequest, "Wrong type of {taskId}")
                return@patch
            }

            try {
                val newTask = call.receive<TaskCreate>()
                call.respond(editTask(newTask))
            } catch (ex: IllegalStateException) {
                call.respond(HttpStatusCode.BadRequest)
            } catch (ex: JsonConvertException) {
                call.respond(HttpStatusCode.BadRequest)
            }
        }

        patch("/edit-habit/{habitId}") {
            val habitId = call.parameters["habitId"]

            if(habitId == null) {
                call.respond(HttpStatusCode.BadRequest,"No {habitId} parameter")
                return@patch
            }

            if(!Utility.checkUuid(habitId)) {
                call.respond(HttpStatusCode.BadRequest, "Wrong type of {habitId}")
                return@patch
            }

            try {
                val newHabit = call.receive<HabitCreate>()
                call.respond(editHabit(newHabit))
            } catch (ex: IllegalStateException) {
                call.respond(HttpStatusCode.BadRequest)
            } catch (ex: JsonConvertException) {
                call.respond(HttpStatusCode.BadRequest)
            }
        }

        delete("/delete-task/{taskId}") {
            val taskId = call.parameters["taskId"]

            if(taskId == null) {
                call.respond(HttpStatusCode.BadRequest,"No {taskId} parameter")
                return@delete
            }

            if(!Utility.checkUuid(taskId)) {
                call.respond(HttpStatusCode.BadRequest, "Wrong type of {taskId}")
                return@delete
            }

            call.respond(deleteTask(taskId))
        }

        delete("/delete-habit/{habitId}") {
            val habitId = call.parameters["habitId"]

            if(habitId == null) {
                call.respond(HttpStatusCode.BadRequest, "No {habitId} parameter")
                return@delete
            }

            if(!Utility.checkUuid(habitId)) {
                call.respond(HttpStatusCode.BadRequest, "Wrong type of {habitId}")
                return@delete
            }

            call.respond(deleteHabit(habitId))
        }
    }
}