package com.rbetik12.lab1

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.query.QueryByExampleExecutor

interface ProductRepo : JpaRepository<Product, Long>, QueryByExampleExecutor<Product> {

}