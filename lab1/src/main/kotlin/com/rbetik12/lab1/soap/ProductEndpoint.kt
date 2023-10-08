package com.rbetik12.lab1.soap

import com.rbetik12.lab1.ProductRepo
import com.rbetik12.generated.GetProductRequest
import com.rbetik12.generated.GetProductResponse
import com.rbetik12.generated.ProductXSD
import com.rbetik12.lab1.Product
import org.springframework.data.domain.Example
import org.springframework.ws.server.endpoint.annotation.Endpoint
import org.springframework.ws.server.endpoint.annotation.PayloadRoot
import org.springframework.ws.server.endpoint.annotation.RequestPayload
import org.springframework.ws.server.endpoint.annotation.ResponsePayload

@Endpoint
class ProductEndpoint(private val productRepo: ProductRepo) {

    init {
        val testProduct = Product(
            id = 0,
            name = "Product",
            producedBy = "Kek",
            price = 1.0f,
            sellAmount = 20
        )

        productRepo.save(testProduct)
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getProductRequest")
    @ResponsePayload
    fun search(@RequestPayload request: GetProductRequest): GetProductResponse {
        val res = productRepo.findProductsByParams(
            request.id,
            request.name,
            request.price,
            request.sellAmount,
            request.producedBy
        )

        val productList = ArrayList<ProductXSD>()
        res.forEach {
            val p = ProductXSD()
            p.id = it.id
            p.name = it.name
            p.price = it.price
            p.producedBy = it.producedBy
            p.sellAmount = it.sellAmount

            productList.add(p)
        }

        val response = GetProductResponse()
        response.products.addAll(productList)
        return response
    }

    companion object {
        const val NAMESPACE_URI = "http://rbetik12.com/generated"
    }
}