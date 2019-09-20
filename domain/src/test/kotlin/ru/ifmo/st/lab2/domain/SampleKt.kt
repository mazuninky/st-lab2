package ru.ifmo.st.lab2.domain

import ru.ifmo.st.lab2.core.Task
import ru.ifmo.st.lab2.core.TaskState
import java.util.*
import kotlin.random.Random

fun currentTime() = Calendar.getInstance().time.time

fun actualTime(appendix: Int): Date = Date(currentTime() + 5000 + appendix)
fun outdatedTime(appendix: Int): Date = Date(currentTime() - 1 - appendix)

val sampleTask = makeSampleTask(1)

fun makeSampleTask(
    id: Long? = null, name: String = "Sample task$id",
    description: String = "Simple description$id",
    date: Date = Calendar.getInstance().time,
    tags: List<String> = listOf("sample", "tags"),
    state: TaskState = TaskState.Backlog
): Task =
    Task(name, description, date, tags, state, id)

fun makeActualTask(id: Long? = null, appendix: Int = Random.nextInt(1000)) =
    makeSampleTask(id, date = actualTime(appendix))

fun makeActualDoneTask(id: Long? = null, appendix: Int = Random.nextInt(1000)) =
    makeSampleTask(id, date = actualTime(appendix), state = TaskState.Done)

fun makeOutdatedTask(id: Long? = null, appendix: Int = Random.nextInt(100)) =
    makeSampleTask(id, date = outdatedTime(appendix))

fun makeOutdatedDoneTask(id: Long? = null, appendix: Int = Random.nextInt(100)) =
    makeSampleTask(id, date = outdatedTime(appendix), state = TaskState.Done)
