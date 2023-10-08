package com.rbetik12.lab1.soap

import jakarta.xml.bind.annotation.XmlElement
import jakarta.xml.bind.annotation.XmlRootElement

@XmlRootElement(namespace = ProductEndpoint.NAMESPACE_URI, name = "searchRequest")
data class SearchRequest(
    @field:XmlElement
    val id: Long? = null,

    @field:XmlElement
    val name: String? = null,

    @field:XmlElement
    val price: Float? = null,

    @field:XmlElement
    val sellAmount: Long? = null,

    @field:XmlElement
    val producedBy: String? = null
)
