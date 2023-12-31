package com.rbetik12.lab2

import jakarta.jws.WebMethod
import jakarta.jws.WebService

@WebService(serviceName = "ProductWebService")
open class ProductWebService {
    @WebMethod
    open fun read(request: Request): List<Product> {
        if (request.username != "user" || request.password != "password") {
            return emptyList()
        }

        val dao = DAO()
        return dao.persons
    }

    @WebMethod
    open fun create(product: Product): Response {
        val response = Response()
        if (product.username != "user" || product.password != "password") {
            response.code = 403
            response.message = "Incorrect password or username"
            return response
        }

        val dao = DAO()

        if (dao.create(product)) {
            response.code = 200
        }
        else {
            response.code = 400
        }
        return response
    }

    @WebMethod
    open fun delete(request: Request): Response {
        val response = Response()
        if (request.username != "user" || request.password != "password") {
            response.code = 403
            response.message = "Incorrect password or username"
            return response
        }

        val dao = DAO()

        if (dao.delete(request.id)) {
            response.code = 200
        }
        else {
            response.code = 400
        }
        return response
    }

    @WebMethod
    open fun update(product: Product): Response {
        val response = Response()
        if (product.username != "user" || product.password != "password") {
            response.code = 403
            response.message = "Incorrect password or username"
            return response
        }

        val dao = DAO()

        if (dao.update(product)) {
            response.code = 200
        }
        else {
            response.code = 400
        }
        return response
    }

    @WebMethod
    open fun receiveBinary(binary: Binary): Response {
        val response = Response()
        if (binary.username != "user" || binary.password != "password") {
            response.code = 403
            response.message = "Incorrect password or username"
            return response
        }

        response.code = 200
        response.message = "Filename: ${binary.filename}\nPayload: ${binary.payload}"
        return response
    }
}