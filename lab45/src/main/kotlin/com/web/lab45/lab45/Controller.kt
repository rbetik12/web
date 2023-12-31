package com.web.lab45.lab45

import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("api/product")
class Controller(private val repo: ProductRepo) {
    @PostMapping("auth")
    fun auth(@RequestBody user: User, request: HttpServletRequest): ResponseEntity<Unit> {
        if (user.username == "user" && user.password == "password") {
            request.session.invalidate()
            val session = request.getSession(true)
            return ResponseEntity.ok().build()
        }

        return ResponseEntity.badRequest().build()
    }

    @PostMapping
    fun create(@RequestBody product: ProductDTO, request: HttpServletRequest): ResponseEntity<ProductDTO> {
        if (request.getSession(false) == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build()
        }

        val newEntity = repo.save(product)
        return ResponseEntity.ok().body(newEntity)
    }

    @PutMapping
    fun update(@RequestBody product: ProductDTO, request: HttpServletRequest): ResponseEntity<ProductDTO> {
        if (request.getSession(false) == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build()
        }

        val productOpt = repo.findById(product.id)

        if (productOpt.isEmpty)
        {
            return ResponseEntity.notFound().build()
        }

        val updatedProduct = repo.save(product)
        return ResponseEntity.ok().body(updatedProduct)
    }

    @GetMapping("{id}")
    fun read(@PathVariable id: Long, request: HttpServletRequest): ResponseEntity<ProductDTO> {
        if (request.getSession(false) == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build()
        }

        val productOpt = repo.findById(id)

        if (productOpt.isEmpty)
        {
            return ResponseEntity.notFound().build()
        }

        return ResponseEntity.ok().body(productOpt.get())
    }

    @DeleteMapping("{id}")
    fun delete(@PathVariable id: Long, request: HttpServletRequest): ResponseEntity<Unit> {
        if (request.getSession(false) == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build()
        }

        val productOpt = repo.findById(id)

        if (productOpt.isEmpty)
        {
            return ResponseEntity.notFound().build()
        }

        repo.delete(productOpt.get())
        return ResponseEntity.ok().build()
    }
}