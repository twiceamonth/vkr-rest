package ru.mav26.tasks

import io.ktor.http.*
import io.ktor.serialization.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import ru.mav26.Utility
import ru.mav26.getUserLogin
import ru.mav26.tasks.models.api.*

fun Application.tasksRouting(repository: TasksRepository) {
    routing {
        authenticate("jwt") {
            get("/tasks-list") {
                val userLogin = call.getUserLogin()

                if (userLogin == null) {
                    call.respond(HttpStatusCode.BadRequest, "No {userLogin} parameter")
                    return@get
                }

                call.respond(repository.getTasksList(userLogin))
            }

            get("/habit-list") {
                val userLogin = call.getUserLogin()

                if (userLogin == null) {
                    call.respond(HttpStatusCode.BadRequest, "No {userLogin} parameter")
                    return@get
                }

                call.respond(repository.getHabitList(userLogin))
            }

            get("/task-details/{taskId}") {
                val taskId = call.parameters["taskId"]

                if (taskId == null) {
                    call.respond(HttpStatusCode.BadRequest, "No {taskId} parameter")
                    return@get
                }

                if (!Utility.checkUuid(taskId)) {
                    call.respond(HttpStatusCode.BadRequest, "Wrong type of {taskId}")
                    return@get
                }

                call.respond(repository.getTaskDetails(taskId))
            }

            get("/habit-details/{habitId}") {
                val habitId = call.parameters["habitId"]

                if (habitId == null) {
                    call.respond(HttpStatusCode.BadRequest, "No {habitId} parameter")
                    return@get
                }

                if (!Utility.checkUuid(habitId)) {
                    call.respond(HttpStatusCode.BadRequest, "Wrong type of {habitId}")
                    return@get
                }

                call.respond(repository.getHabitDetails(habitId))
            }

            post("/new-task") {
                try {
                    val userLogin = call.getUserLogin()
                    val task = call.receive<TaskCreate>()
                    call.respond(repository.createTask(task, userLogin))
                } catch (ex: IllegalStateException) {
                    call.respond(HttpStatusCode.BadRequest)
                } catch (ex: JsonConvertException) {
                    call.respond(HttpStatusCode.BadRequest)
                }
            }

            post("/new-habit") {
                try {
                    val userLogin = call.getUserLogin()
                    val habit = call.receive<HabitCreate>()
                    call.respond(repository.createHabit(habit, userLogin))
                } catch (ex: IllegalStateException) {
                    call.respond(HttpStatusCode.BadRequest)
                } catch (ex: JsonConvertException) {
                    call.respond(HttpStatusCode.BadRequest)
                }
            }

            post("/new-subtask/{taskId}") {
                val taskId = call.parameters["taskId"]

                if (taskId == null) {
                    call.respond(HttpStatusCode.BadRequest, "No {taskId} parameter")
                    return@post
                }

                if (!Utility.checkUuid(taskId)) {
                    call.respond(HttpStatusCode.BadRequest, "Wrong type of {taskId}")
                    return@post
                }

                try {
                    val st = call.receive<SubtaskCreate>()
                    call.respond(repository.createSubtask(st, taskId))
                } catch (ex: IllegalStateException) {
                    call.respond(HttpStatusCode.BadRequest)
                } catch (ex: JsonConvertException) {
                    call.respond(HttpStatusCode.BadRequest)
                }
            }

            post("/new-details/{taskId}") {
                val taskId = call.parameters["taskId"]

                if (taskId == null) {
                    call.respond(HttpStatusCode.BadRequest, "No {taskId} parameter")
                    return@post
                }

                if (!Utility.checkUuid(taskId)) {
                    call.respond(HttpStatusCode.BadRequest, "Wrong type of {taskId}")
                    return@post
                }

                try {
                    val details = call.receive<DetailsCreate>()
                    call.respond(repository.createDetails(details, taskId))
                } catch (ex: IllegalStateException) {
                    call.respond(HttpStatusCode.BadRequest)
                } catch (ex: JsonConvertException) {
                    call.respond(HttpStatusCode.BadRequest)
                }
            }

            patch("/edit-task/{taskId}") {
                val taskId = call.parameters["taskId"]

                if (taskId == null) {
                    call.respond(HttpStatusCode.BadRequest, "No {taskId} parameter")
                    return@patch
                }

                if (!Utility.checkUuid(taskId)) {
                    call.respond(HttpStatusCode.BadRequest, "Wrong type of {taskId}")
                    return@patch
                }

                try {
                    val newTask = call.receive<TaskEdit>()
                    call.respond(repository.editTask(newTask, taskId))
                } catch (ex: IllegalStateException) {
                    call.respond(HttpStatusCode.BadRequest)
                } catch (ex: JsonConvertException) {
                    call.respond(HttpStatusCode.BadRequest)
                }
            }

            patch("/edit-subtask/{subtaskId}") {
                val subtaskId = call.parameters["subtaskId"]

                if (subtaskId == null) {
                    call.respond(HttpStatusCode.BadRequest, "No {subtaskId} parameter")
                    return@patch
                }

                if (!Utility.checkUuid(subtaskId)) {
                    call.respond(HttpStatusCode.BadRequest, "Wrong type of {subtaskId}")
                    return@patch
                }

                try {
                    val newSubTask = call.receive<SubtaskEdit>()
                    call.respond(repository.editSubtask(newSubTask, subtaskId))
                } catch (ex: IllegalStateException) {
                    call.respond(HttpStatusCode.BadRequest)
                } catch (ex: JsonConvertException) {
                    call.respond(HttpStatusCode.BadRequest)
                }
            }

            patch("/edit-habit/{habitId}") {
                val habitId = call.parameters["habitId"]

                if (habitId == null) {
                    call.respond(HttpStatusCode.BadRequest, "No {habitId} parameter")
                    return@patch
                }

                if (!Utility.checkUuid(habitId)) {
                    call.respond(HttpStatusCode.BadRequest, "Wrong type of {habitId}")
                    return@patch
                }

                try {
                    val newHabit = call.receive<HabitEdit>()
                    call.respond(repository.editHabit(newHabit, habitId))
                } catch (ex: IllegalStateException) {
                    call.respond(HttpStatusCode.BadRequest)
                } catch (ex: JsonConvertException) {
                    call.respond(HttpStatusCode.BadRequest)
                }
            }

            patch("/edit-details/{detailsId}") {
                val detailsId = call.parameters["detailsId"]

                if (detailsId == null) {
                    call.respond(HttpStatusCode.BadRequest, "No {detailsId} parameter")
                    return@patch
                }

                if (!Utility.checkUuid(detailsId)) {
                    call.respond(HttpStatusCode.BadRequest, "Wrong type of {detailsId}")
                    return@patch
                }

                try {
                    val newDetails = call.receive<DetailsEdit>()
                    call.respond(repository.editDetails(newDetails, detailsId))
                } catch (ex: IllegalStateException) {
                    call.respond(HttpStatusCode.BadRequest)
                } catch (ex: JsonConvertException) {
                    call.respond(HttpStatusCode.BadRequest)
                }
            }

            delete("/delete-task/{taskId}") {
                val taskId = call.parameters["taskId"]

                if (taskId == null) {
                    call.respond(HttpStatusCode.BadRequest, "No {taskId} parameter")
                    return@delete
                }

                if (!Utility.checkUuid(taskId)) {
                    call.respond(HttpStatusCode.BadRequest, "Wrong type of {taskId}")
                    return@delete
                }

                call.respond(repository.deleteTask(taskId))
            }

            delete("/delete-habit/{habitId}") {
                val habitId = call.parameters["habitId"]

                if (habitId == null) {
                    call.respond(HttpStatusCode.BadRequest, "No {habitId} parameter")
                    return@delete
                }

                if (!Utility.checkUuid(habitId)) {
                    call.respond(HttpStatusCode.BadRequest, "Wrong type of {habitId}")
                    return@delete
                }

                call.respond(repository.deleteHabit(habitId))
            }

            delete("/delete-subtask/{subtaskId}") {
                val subtaskId = call.parameters["subtaskId"]

                if (subtaskId == null) {
                    call.respond(HttpStatusCode.BadRequest, "No {subtaskId} parameter")
                    return@delete
                }

                if (!Utility.checkUuid(subtaskId)) {
                    call.respond(HttpStatusCode.BadRequest, "Wrong type of {subtaskId}")
                    return@delete
                }

                call.respond(repository.deleteSubtask(subtaskId))
            }

            delete("/delete-details/{detailsId}") {
                val detailsId = call.parameters["detailsId"]

                if (detailsId == null) {
                    call.respond(HttpStatusCode.BadRequest, "No {detailsId} parameter")
                    return@delete
                }

                if (!Utility.checkUuid(detailsId)) {
                    call.respond(HttpStatusCode.BadRequest, "Wrong type of {detailsId}")
                    return@delete
                }

                call.respond(repository.deleteDetails(detailsId))
            }
        }
    }
}