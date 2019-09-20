package ru.ifmo.st.lab2.data.jdbc

import java.sql.Connection
import java.sql.DriverManager
import java.sql.Statement


class DB {
//    private val dataSource: HikariDataSource

    val connection: Connection = DriverManager.getConnection(
        "jdbc:postgresql://localhost:5432/todo",
        "todo",
        "12345"
    )

    init {
        //        val source = HikariDataSource()
//        source.driverClassName = "org.postgresql.Driver"
//        source.jdbcUrl = "jdbc:postgresql://localhost:5432/todo"
//        source.username = "todo"
//        source.password = "12345"
//        dataSource = source
    }


    inline fun <T> executeStatement(body: (Statement) -> T): T {
        return connection.createStatement().use(body)
    }
}
