package com.rbetik12.lab1

import jakarta.persistence.*

@Entity
@Table(name = "Products")
data class Product(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(nullable = false)
    var name: String = "",

    @Column(nullable = false)
    var price: Float = 0.0f,

    @Column(nullable = false)
    var sellAmount: Long = -1,

    @Column(nullable = false)
    var producedBy: String = "",
)
