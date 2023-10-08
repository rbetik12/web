package com.rbetik12.lab1.soap

import com.rbetik12.lab1.ProductRepo
import com.rbetik12.generated.GetProductRequest
import com.rbetik12.generated.GetProductResponse
import com.rbetik12.generated.ProductXSD
import org.springframework.ws.server.endpoint.annotation.Endpoint
import org.springframework.ws.server.endpoint.annotation.PayloadRoot
import org.springframework.ws.server.endpoint.annotation.RequestPayload
import org.springframework.ws.server.endpoint.annotation.ResponsePayload

@Endpoint
class ProductEndpoint(private val productRepo: ProductRepo) {
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getProductRequest")
    @ResponsePayload
    fun search(@RequestPayload request: GetProductRequest): GetProductResponse {
        val p = ProductXSD()

        val response = GetProductResponse()
        p.id = 1
        p.name = "kek"
        p.price = 1.0f
        p.producedBy = "kekekek"
        p.sellAmount = 20

        val p1 = ProductXSD()
        p1.id = 2
        p1.name = "kek1"
        p1.price = 2.0f
        p1.producedBy = "kek"
        p1.sellAmount = 30

        response.products.addAll(arrayOf(p1, p))
        return response
    }

    companion object {
        const val NAMESPACE_URI = "http://rbetik12.com/generated"
    }
}