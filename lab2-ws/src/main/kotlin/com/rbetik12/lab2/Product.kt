package com.rbetik12.lab2

open class Product(
    var username: String,
    var password: String,
    var id: Long = 0,
    var name: String = "",
    var producedBy: String = "",
    var price: Float = 0.0f,
    var sellAmount: Long = 0
)