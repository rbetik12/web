package com.rbetik12.lab1

import org.springframework.core.io.ClassPathResource
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.http.HttpStatus
import org.apache.commons.io.IOUtils

@RestController
@RequestMapping("/files")
class FileController {

    @GetMapping("xsd")
    fun downloadFile(): ResponseEntity<ByteArray> {
        try {
            // Load the file from the resources directory
            val resource = ClassPathResource("products.xsd")

            // Check if the file exists
            return if (resource.exists()) {
                val fileData = IOUtils.toByteArray(resource.inputStream)

                val headers = HttpHeaders()
                headers.contentType = MediaType.APPLICATION_OCTET_STREAM
                headers.setContentDispositionFormData("attachment", "products.xsd")

                ResponseEntity(fileData, headers, HttpStatus.OK)
            } else {
                ResponseEntity(HttpStatus.NOT_FOUND)
            }
        } catch (e: Exception) {
            // Handle the exception appropriately
            return ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }

    @GetMapping("wsdl")
    fun downloadFileWsdl(): ResponseEntity<ByteArray> {
        try {
            // Load the file from the resources directory
            val resource = ClassPathResource("products.wsdl")

            // Check if the file exists
            return if (resource.exists()) {
                val fileData = IOUtils.toByteArray(resource.inputStream)

                val headers = HttpHeaders()
                headers.contentType = MediaType.APPLICATION_OCTET_STREAM
                headers.setContentDispositionFormData("attachment", "products.wsdl")

                ResponseEntity(fileData, headers, HttpStatus.OK)
            } else {
                ResponseEntity(HttpStatus.NOT_FOUND)
            }
        } catch (e: Exception) {
            // Handle the exception appropriately
            return ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }
}
