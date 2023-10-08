package com.rbetik12.lab1

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.QueryByExampleExecutor

interface ProductRepo : JpaRepository<Product, Long>, QueryByExampleExecutor<Product> {
    @Query(value = "select * from Products where id = :id or name = :name or price < :price or sell_amount = :sellAmount or produced_by = :producedBy", nativeQuery = true)
    fun findProductsByParams(id: Long,
                             name: String,
                             price: Float,
                             sellAmount: Long,
                             producedBy: String): List<Product>
}