package com.rbetik12.lab1.soap

import com.rbetik12.generated.xsd.GetProductResponse
import com.rbetik12.generated.xsd.ProductXSD
import com.rbetik12.generated.xsd.StatusResponse
import com.rbetik12.lab1.Product
import com.rbetik12.lab1.ProductRepo
import jakarta.xml.bind.JAXBElement
import localhost._8081.products.soap.*
import org.springframework.ws.server.endpoint.annotation.Endpoint
import org.springframework.ws.server.endpoint.annotation.PayloadRoot
import org.springframework.ws.server.endpoint.annotation.RequestPayload
import org.springframework.ws.server.endpoint.annotation.ResponsePayload
import javax.xml.namespace.QName

@Endpoint
class ProductEndpoint(private val productRepo: ProductRepo) {
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "read")
    @ResponsePayload
    fun read(@RequestPayload readRequest: Read): ReadResponse {
        val reqInstance = readRequest.request.value
        val res = productRepo.findProductsByParams(
            reqInstance.id.value,
            reqInstance.name.value,
            reqInstance.price.value,
            reqInstance.sellAmount.value,
            reqInstance.producedBy.value
        )

        val productList = ArrayList<ProductXSD>()
        res.forEach {
            val p = ProductXSD()
            p.id = JAXBElement(QName("http://generated.rbetik12.com/xsd", "id"), Long::class.java, it.id)
            p.name= JAXBElement(QName("http://generated.rbetik12.com/xsd", "name"), String::class.java, it.name)
            p.price = JAXBElement(QName("http://generated.rbetik12.com/xsd", "price"), Float::class.java, it.price)
            p.producedBy = JAXBElement(QName("http://generated.rbetik12.com/xsd", "producedBy"), String::class.java, it.producedBy)
            p.sellAmount = JAXBElement(QName("http://generated.rbetik12.com/xsd", "sellAmount"), Long::class.java, it.sellAmount)

            productList.add(p)
        }

        val factory = ObjectFactory()
        val response = factory.createReadResponse()
        response.`return` = factory.createReadResponseReturn(GetProductResponse())
        response.`return`.value.products.addAll(productList)
        return response
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "create")
    @ResponsePayload
    fun create(@RequestPayload request: Create): CreateResponse {
        val reqInstance = request.request.value
        val newProduct = Product(
            id = 0,
            name = reqInstance.name.value,
            producedBy = reqInstance.producedBy.value,
            sellAmount = reqInstance.sellAmount,
            price = reqInstance.price
        )

        productRepo.save(newProduct)

        val factory = ObjectFactory()
        val response = factory.createCreateResponse()
        response.`return` = factory.createCreateResponseReturn(StatusResponse())
        response.`return`.value.code = 200
        return response
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "delete")
    @ResponsePayload
    fun delete(@RequestPayload request: Delete): DeleteResponse {
        val id = request.request.value.id.value

        productRepo.deleteById(id)

        val factory = ObjectFactory()
        val response = factory.createDeleteResponse()
        response.`return` = factory.createDeleteResponseReturn(StatusResponse())
        response.`return`.value.code = 200
        return response
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "update")
    @ResponsePayload
    fun update(@RequestPayload request: Update): UpdateResponse {
        val productOpt = productRepo.findById(request.request.value.id.value)
        val factory = ObjectFactory()

        if (productOpt.isEmpty) {
            val response = factory.createUpdateResponse()
            response.`return` = factory.createUpdateResponseReturn(StatusResponse())
            response.`return`.value.code = 404
            response.`return`.value.errorMessage = JAXBElement(QName("http://generated.rbetik12.com/xsd", "errorMessage"), String::class.java, "Can't find entity with id ${request.request.value.id.value} for update")
            return response
        }

        val product = productOpt.get()
        product.name = request.request.value.name.value
        product.price = request.request.value.price
        product.producedBy = request.request.value.producedBy.value
        product.sellAmount = request.request.value.sellAmount

        productRepo.save(product)

        val response = factory.createUpdateResponse()
        response.`return` = factory.createUpdateResponseReturn(StatusResponse())
        response.`return`.value.code = 200
        return response
    }

    companion object {
        const val NAMESPACE_URI = "http://localhost:8081/products/soap"
    }
}