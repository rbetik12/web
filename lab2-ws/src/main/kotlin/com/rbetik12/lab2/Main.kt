package com.rbetik12.lab2

import jakarta.xml.ws.Endpoint

fun main(args: Array<String>) {
    val url = "http://localhost:8081/ProductWebService"

    Endpoint.publish(url, ProductWebService())
}