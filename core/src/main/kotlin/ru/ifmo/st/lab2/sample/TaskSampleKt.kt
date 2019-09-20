package ru.ifmo.st.lab2.sample

import ru.ifmo.st.lab2.core.Task
import ru.ifmo.st.lab2.core.TaskState
import java.util.*
import kotlin.random.Random

fun Calendar.clearTime(): Calendar = apply {
    set(Calendar.HOUR_OF_DAY, 0)
    set(Calendar.MINUTE, 0)
    set(Calendar.SECOND, 0)
    set(Calendar.MILLISECOND, 0)
}


fun currentTime() = Calendar.getInstance().clearTime().time.time

fun actualTime(appendix: Int): Date = Date(currentTime() + 5000 + appendix)
fun outdatedTime(appendix: Int): Date = Date(currentTime() - 1 - appendix)

val sampleTask = makeSampleTask(1)

fun makeFixedTask(id: Long) = makeSampleTask(id, date = Date(0))

fun makeSampleTask(
    id: Long? = null, name: String = "Sample task$id",
    description: String = "Simple description$id",
    date: Date = Calendar.getInstance().clearTime().time,
    tags: List<String> = listOf("sample", "tags"),
    state: TaskState = TaskState.Backlog
): Task =
    Task(name, description, date, tags, state, id)

fun makeActualTask(id: Long? = null, appendix: Int = Random.nextInt(1000)) =
    makeSampleTask(id, date = actualTime(appendix))

fun makeActualDoneTask(id: Long? = null, appendix: Int = Random.nextInt(1000)) =
    makeSampleTask(
        id,
        date = actualTime(appendix),
        state = TaskState.Done
    )

fun makeOutdatedTask(id: Long? = null, appendix: Int = Random.nextInt(100)) =
    makeSampleTask(id, date = outdatedTime(appendix))

fun makeOutdatedDoneTask(id: Long? = null, appendix: Int = Random.nextInt(100)) =
    makeSampleTask(
        id,
        date = outdatedTime(appendix),
        state = TaskState.Done
    )
