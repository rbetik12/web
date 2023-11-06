package com.rbetik12.lab2

import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException
import java.sql.Statement
import java.util.logging.Level
import java.util.logging.Logger

object ConnectionUtil {
    private const val JDBC_URL = "jdbc:postgresql://localhost:5432/db"
    private const val JDBC_USER = "kek"
    private const val JDBC_PASSWORD = "lolkek"

    init {
        try {
            Class.forName("org.postgresql.Driver")
        } catch (ex: ClassNotFoundException) {
            Logger.getLogger(DAO::class.java.name).log(
                Level.SEVERE, null,
                ex
            )
        }

        connection.use { connection ->
            val statement: Statement = connection!!.createStatement()

            val createTableSQL = ("CREATE TABLE IF NOT EXISTS products ("
                    + "id BIGSERIAL PRIMARY KEY,"
                    + "name VARCHAR(255) NOT NULL,"
                    + "producedBy VARCHAR(255) NOT NULL,"
                    + "price REAL NOT NULL,"
                    + "sellAmount BIGINT NOT NULL"
                    + ")")

            statement.executeUpdate(createTableSQL)
            println("Table 'products' created (if it didn't exist).")
        }
    }

    val connection: Connection?
        get() {
            var connection: Connection? = null
            try {
                connection = DriverManager.getConnection(
                    JDBC_URL, JDBC_USER,
                    JDBC_PASSWORD
                )
            } catch (ex: SQLException) {
                Logger.getLogger(ConnectionUtil::class.java.name).log(
                    Level.SEVERE, null,
                    ex
                )
            }
            return connection
        }
}