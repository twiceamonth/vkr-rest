package ru.mav26.tasks

import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction
import ru.mav26.tasks.models.api.*
import ru.mav26.tasks.models.db.*
import java.time.LocalTime
import java.time.OffsetDateTime
import java.util.*


class TasksRepository {

    fun getTasksList(): List<TasksListResponse> {
        return transaction {
            val tasks = TaskTable.selectAll().toList()
            val taskIds = tasks.map { it[TaskTable.taskId] }

            val subtasksByTask = SubtaskTable
                .selectAll().where { SubtaskTable.taskId inList taskIds }
                .groupBy { it[SubtaskTable.taskId] }

            tasks.map { taskRow ->
                val currentTaskId = taskRow[TaskTable.taskId]
                TasksListResponse(
                    taskId = currentTaskId.toString(),
                    title = taskRow[TaskTable.title],
                    endTime = taskRow[TaskTable.endTime]?.toString(),
                    difficulty = taskRow[TaskTable.difficulty],
                    priority = taskRow[TaskTable.priority],
                    frequency = taskRow[TaskTable.frequency],
                    status = taskRow[TaskTable.status],
                    timerInterval = taskRow[TaskTable.timerInterval]?.toString(),
                    description = taskRow[TaskTable.description],
                    subtasks = subtasksByTask[currentTaskId]?.map { subtaskRow ->
                        SubtaskResponse(
                            subtaskId = subtaskRow[SubtaskTable.subtaskId].toString(),
                            status = subtaskRow[SubtaskTable.status],
                            taskId = currentTaskId.toString(),
                            title = subtaskRow[SubtaskTable.title]
                        )
                    } ?: emptyList()
                )
            }
        }
    }

    fun getHabitList(): List<HabitListResponse> {
        return transaction {
            HabitTable.selectAll().map { habit ->
                HabitListResponse(
                    habitId = habit[HabitTable.habitId].toString(),
                    title = habit[HabitTable.title],
                    difficulty =  habit[HabitTable.difficulty],
                    frequency =  habit[HabitTable.frequency],
                    timerInterval =  habit[HabitTable.timerInterval].toString(),
                    description =  habit[HabitTable.description],
                    streakCount =  habit[HabitTable.streakCount]
                )
            }
        }
    }

    fun getTaskDetails(id: String): TaskDetailsResponse {
        return transaction {
            TaskTable.selectAll().where(TaskTable.taskId eq UUID.fromString(id)).map { row ->
                TaskDetailsResponse(
                    taskId = row[TaskTable.taskId].toString(),
                    title = row[TaskTable.title],
                    endTime = row[TaskTable.endTime].toString(),
                    difficulty =
                        DifficultyTable.selectAll().where(DifficultyTable.difficultyName eq row[TaskTable.difficulty])
                            .map { d ->
                                DifficultyResponse(
                                    difficultyName = d[DifficultyTable.difficultyName],
                                    multiplier = d[DifficultyTable.multiplier]
                                )
                            }.first(),
                    priority =
                        PriorityTable.selectAll().where(PriorityTable.priorityName eq row[TaskTable.priority])
                            .map { p ->
                                PriorityResponse(
                                    priorityName = p[PriorityTable.priorityName],
                                    multiplier = p[PriorityTable.multiplier]
                                )
                            }.first(),
                    frequency = FrequencyResponse(row[TaskTable.frequency]),
                    details = DetailsTable.selectAll().where(DetailsTable.taskId eq UUID.fromString(id))
                        .map { d ->
                            DetailsResponse(
                                detailsId = d[DetailsTable.detailsId].toString(),
                                linkUrl = d[DetailsTable.linkUrl],
                                linkName = d[DetailsTable.linkName]
                            )
                        },
                    status = row[TaskTable.status!!],
                    timerInterval = row[TaskTable.timerInterval].toString(),
                    description = row[TaskTable.description],
                    createdAt = row[TaskTable.createdAt].toString(),
                    finishedAt = row[TaskTable.finishedAt]?.toString(),
                    subtasks = SubtaskTable.selectAll().where(SubtaskTable.taskId eq row[TaskTable.taskId])
                        .map { subtaskRow ->
                            SubtaskResponse(
                                subtaskRow[SubtaskTable.subtaskId].toString(),
                                subtaskRow[SubtaskTable.title],
                                subtaskRow[SubtaskTable.status!!],
                                row[TaskTable.taskId].toString()
                            )
                        }
                )
            }.first()
        }
    }

    fun getHabitDetails(id: String): HabitDetailsResponse {
        return transaction {
            HabitTable.selectAll().where(HabitTable.habitId eq UUID.fromString(id)).map { habit ->
                HabitDetailsResponse(
                    habitId = habit[HabitTable.habitId].toString(),
                    title = habit[HabitTable.title],
                    difficulty = DifficultyTable.selectAll()
                        .where { DifficultyTable.difficultyName eq habit[HabitTable.difficulty] }
                        .map { d ->
                            DifficultyResponse(
                                difficultyName = d[DifficultyTable.difficultyName],
                                multiplier = d[DifficultyTable.multiplier]
                            )
                        }.first(),
                    frequency =  FrequencyTable.selectAll().where(FrequencyTable.frequencyName eq habit[HabitTable.frequency])
                        .map { f ->
                            FrequencyResponse(
                                frequencyName = f[FrequencyTable.frequencyName]
                            )
                        }.first(),
                    timerInterval = habit[HabitTable.timerInterval].toString(),
                    description =  habit[HabitTable.description],
                    createdAt = habit[HabitTable.createdAt].toString(),
                    streakCount =  habit[HabitTable.streakCount],
                    lastPerformedAt = habit[HabitTable.lastPerformedAt].toString()
                )
            }
        }.first()
    }

    fun createTask(task: TaskCreate) {
        return transaction {
            val insertStatement = TaskTable.insert {
                it[userLogin] = task.userLogin
                it[title] = task.title
                if(task.endTime != null) it[endTime] = OffsetDateTime.parse(task.endTime)
                it[difficulty] = task.difficulty
                it[priority] = task.priority
                it[frequency] = task.frequency
                if(task.timerInterval != null) it[timerInterval] = LocalTime.parse(task.timerInterval)
                it[description] = task.description
                it[baseExp] = 1
            }

            val taskId = insertStatement[TaskTable.taskId]

            for (subtaskTitle in task.subtasks) {
                SubtaskTable.insert {
                    it[title] = subtaskTitle
                    it[SubtaskTable.taskId] = taskId
                }
            }
        }
    }

    fun createHabit(habit: HabitCreate) {
        return transaction {
            HabitTable.insert {
                it[userLogin] = habit.userLogin
                it[title] = habit.title
                it[difficulty] = habit.difficulty
                it[frequency] = habit.frequency
                it[timerInterval] = if(habit.timerInterval != null) LocalTime.parse(habit.timerInterval) else null
                it[description] = habit.description
                it[baseExp] = 1
            }
        }
    }

    fun createSubtask(st: SubtaskCreate, taskId: String) {
        return transaction {
            SubtaskTable.insert {
                it[title] = st.title
                it[SubtaskTable.taskId] = UUID.fromString(taskId)
            }
        }
    }

    fun createDetails(details: DetailsCreate, taskId: String) {
        return transaction {
            DetailsTable.insert {
                it[linkUrl] = details.linkUrl
                it[linkName] = details.linkName
                it[DetailsTable.taskId] = UUID.fromString(taskId)
            }
        }
    }

    fun editTask(newTask: TaskEdit, taskId: String) {
        return transaction {
            val taskUUID = UUID.fromString(taskId)
            TaskTable.update({ TaskTable.taskId eq taskUUID }) { row ->
                newTask.title?.let { row[title] = it }
                row[endTime] = newTask.endTime?.let { OffsetDateTime.parse(it) }
                newTask.difficulty?.let { row[difficulty] = it }
                newTask.priority?.let { row[priority] = it }
                newTask.frequency?.let { row[frequency] = it }
                newTask.status?.let { row[status] = it }
                row[timerInterval] = newTask.timerInterval?.let { LocalTime.parse(it) }
                newTask.description?.let { row[description] = it }
                row[finishedAt] = newTask.finishedAt?.let { OffsetDateTime.parse(it) }
            }
        }
    }

    fun editSubtask(newSubtask: SubtaskEdit, subtaskId: String) {
        return transaction {
            SubtaskTable.update({ SubtaskTable.subtaskId eq UUID.fromString(subtaskId) }) { row ->
                newSubtask.title?.let { row[title] = it }
                newSubtask.status?.let { row[status] = it }
            }
        }
    }

    fun editHabit(newHabit: HabitEdit, habitId: String) {
        transaction {
            HabitTable.update({ HabitTable.habitId eq UUID.fromString(habitId) }) { row ->
                newHabit.title?.let { row[title] = it }
                newHabit.difficulty?.let { row[difficulty] = it }
                newHabit.frequency?.let { row[frequency] = it }
                row[timerInterval] = newHabit.timerInterval?.let { LocalTime.parse(it) }
                newHabit.description?.let { row[description] = it }
                newHabit.streakCount?.let { row[streakCount] = it }
                newHabit.lastPerformedAt?.let { row[lastPerformedAt] = OffsetDateTime.parse(it) }
                newHabit.userLogin?.let { row[userLogin] = it }
            }
        }
    }

    fun editDetails(newDetails: DetailsEdit, detailsId: String) {
        return transaction {
            DetailsTable.update({ DetailsTable.detailsId eq UUID.fromString(detailsId) }) { row ->
                newDetails.linkUrl?.let { row[linkUrl] = it }
                newDetails.linkName?.let { row[linkName] = it }
            }
        }
    }

    fun deleteTask(id: String) {
        return transaction {
            SubtaskTable.deleteWhere { taskId eq UUID.fromString(id) }
            TaskTable.deleteWhere { taskId eq UUID.fromString(id) }
        }
    }

    fun deleteHabit(id: String) {
        return transaction {
            HabitTable.deleteWhere { habitId eq UUID.fromString(id) }
        }
    }

    fun deleteSubtask(id: String) {
        return transaction {
            SubtaskTable.deleteWhere { subtaskId eq UUID.fromString(id) }
        }
    }

    fun deleteDetails(id: String) {
        return transaction {
            DetailsTable.deleteWhere { detailsId eq UUID.fromString(id) }
        }
    }

}