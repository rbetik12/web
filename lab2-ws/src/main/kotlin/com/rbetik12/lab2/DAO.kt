package com.rbetik12.lab2

import java.sql.SQLException
import java.util.logging.Level
import java.util.logging.Logger

class DAO {
    val persons: List<Product>
        get() {
            val persons: MutableList<Product> = ArrayList()
            try {
                ConnectionUtil.connection.use { connection ->
                    val stmt = connection!!.createStatement()
                    val rs = stmt.executeQuery("select * from products")
                    while (rs.next()) {
                        val id = rs.getLong("id")
                        val producedBy = rs.getString("producedBy")
                        val name = rs.getString("name")
                        val price = rs.getFloat("price")
                        val sellAmount = rs.getLong("sellAmount")
                        val person = Product("", "", id, name, producedBy, price, sellAmount)
                        persons.add(person)
                    }
                }
            } catch (ex: SQLException) {
                Logger.getLogger(DAO::class.java.name).log(
                    Level.SEVERE,
                    null, ex
                )
                throw IllegalOperationException(ex.message, IllegalOperationFault())
            }
            return persons
        }

    fun create(product: Product): Boolean {
        try {
            ConnectionUtil.connection.use { connection ->
                val insertQuery = "INSERT INTO products (id, name, producedBy, price, sellAmount) VALUES (?, ?, ?, ?, ?)"
                val stmt = connection!!.prepareStatement(insertQuery)
                stmt.setLong(1, product.id)
                stmt.setString(2, product.name)
                stmt.setString(3, product.producedBy)
                stmt.setFloat(4, product.price)
                stmt.setLong(5, product.sellAmount)

                val rowsInserted = stmt.executeUpdate()

                if (rowsInserted > 0) {
                    return true
                }
            }
        } catch (ex: SQLException) {
            Logger.getLogger(DAO::class.java.name).log(
                Level.SEVERE,
                null, ex
            )

            throw IllegalOperationException(ex.message, IllegalOperationFault())
        }
        return false
    }

    fun update(product: Product): Boolean {
        try {
            ConnectionUtil.connection.use { connection ->
                val updateQuery = "update products set name = ?, producedBy = ?, price = ?, sellAmount = ? where id = ?"
                val stmt = connection!!.prepareStatement(updateQuery)

                stmt.setString(1, product.name)
                stmt.setString(2, product.producedBy)
                stmt.setFloat(3, product.price)
                stmt.setLong(4, product.sellAmount)
                stmt.setLong(5, product.id)

                val rowsUpdated = stmt.executeUpdate()

                if (rowsUpdated > 0) {
                    return true
                }
            }
        } catch (ex: SQLException) {
            Logger.getLogger(DAO::class.java.name).log(
                Level.SEVERE,
                null, ex
            )
            throw IllegalOperationException(ex.message, IllegalOperationFault())
        }
        return false
    }

    fun delete(id: Long): Boolean {
        try {
            ConnectionUtil.connection.use { connection ->
                val insertQuery = "delete from products where id = ?"
                val stmt = connection!!.prepareStatement(insertQuery)
                stmt.setLong(1, id)

                val rowsDeleted = stmt.executeUpdate()

                if (rowsDeleted > 0) {
                    return true
                }
            }
        } catch (ex: SQLException) {
            Logger.getLogger(DAO::class.java.name).log(
                Level.SEVERE,
                null, ex
            )
            throw IllegalOperationException(ex.message, IllegalOperationFault())
        }
        return false
    }
}
