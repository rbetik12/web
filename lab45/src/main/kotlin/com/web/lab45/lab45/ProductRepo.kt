package com.web.lab45.lab45

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ProductRepo: JpaRepository<ProductDTO, Long> {
}