package ru.ifmo.st.lab2.program

import java.text.DateFormat
import java.util.*
import kotlin.NoSuchElementException


fun DateFormat.parseOrNull(source: String): Date? {
    return try {
        parse(source)
    } catch (e: Exception) {
        null
    }
}


fun <T> List<T>.second(): T {
    if (isEmpty())
        throw NoSuchElementException("List is empty.")
    return this[1]
}
