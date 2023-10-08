package com.rbetik12.lab1

import jakarta.persistence.*

@Entity
@Table(name = "Products")
data class Product(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(nullable = false)
    val name: String = "",

    @Column(nullable = false)
    val price: Float = 0.0f,

    @Column(nullable = false)
    val sellAmount: Long = -1,

    @Column(nullable = false)
    val producedBy: String = "",
)
