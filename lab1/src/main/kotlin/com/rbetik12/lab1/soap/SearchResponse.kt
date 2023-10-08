package com.rbetik12.lab1.soap

import com.rbetik12.lab1.Product
import jakarta.xml.bind.annotation.XmlElement
import jakarta.xml.bind.annotation.XmlRootElement

@XmlRootElement(namespace = ProductEndpoint.NAMESPACE_URI, name = "searchResponse")
data class SearchResponse(
    @field:XmlElement
    val products: MutableList<Product> = ArrayList()
)
