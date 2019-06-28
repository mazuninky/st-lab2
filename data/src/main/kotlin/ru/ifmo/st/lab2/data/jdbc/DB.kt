package ru.ifmo.st.lab2.data.jdbc

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import java.sql.Connection
import java.sql.Statement

//val isProd = System.getenv("PROD")?.toBoolean() ?: false

class DB {
    private val dataSource: HikariDataSource

    init {
        val source = HikariDataSource()
        source.driverClassName = "org.postgresql.Driver"
        source.jdbcUrl = "jdbc:postgresql://localhost:5432/todo"
        source.username = "todo"
        source.password = "12345"
        dataSource = source
    }

    val connection: Connection = dataSource.connection

    inline fun <T> executeStatement(body: (Statement) -> T): T {
        return connection.createStatement().use(body)
    }
}
