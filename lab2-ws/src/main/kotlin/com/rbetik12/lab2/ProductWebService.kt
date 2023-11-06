package com.rbetik12.lab2

import jakarta.jws.WebMethod
import jakarta.jws.WebService

@WebService(serviceName = "ProductWebService")
open class ProductWebService {
    @WebMethod
    open fun read(): List<Product> {
        val dao = DAO()
        return dao.persons
    }

    @WebMethod
    open fun create(product: Product): Response {
        val dao = DAO()
        val response = Response()

        if (dao.create(product)) {
            response.code = 200
        }
        else {
            response.code = 400
        }
        return response
    }

    @WebMethod
    open fun delete(id: Long): Response {
        val dao = DAO()
        val response = Response()

        if (dao.delete(id)) {
            response.code = 200
        }
        else {
            response.code = 400
        }
        return response
    }

    @WebMethod
    open fun update(product: Product): Response {
        val dao = DAO()
        val response = Response()

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
        response.code = 200
        response.message = "Filename: ${binary.filename}\nPayload: ${binary.payload}"
        return response
    }
}