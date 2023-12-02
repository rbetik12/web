package com.web.lab45.lab45

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.*

@Entity
@Table(name = "products")
data class ProductDTO(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(nullable = false, unique = true)
    val name: String = "",

    @Column(nullable = false)
    val producedBy: String = "",

    @Column(nullable = false)
    val price: Float = 0.0f,

    @Column(nullable = false)
    val sellAmount: Long = 0
)
