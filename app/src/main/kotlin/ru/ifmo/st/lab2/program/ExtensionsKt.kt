package ru.ifmo.st.lab2.program

import java.lang.Exception
import java.security.CodeSource
import java.text.DateFormat
import java.text.ParseException
import java.util.*

fun DateFormat.parseOrNull(source: String): Date? {
    return try {
        parse(source)
    } catch (e: Exception) {
        null
    }
}
