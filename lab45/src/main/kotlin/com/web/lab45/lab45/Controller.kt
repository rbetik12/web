package com.web.lab45.lab45

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("api/rest")
class Controller(private val repo: ProductRepo) {
    @PostMapping("new")
    fun create(@RequestBody product: ProductDTO): ResponseEntity<ProductDTO> {
        val newEntity = repo.save(product)
        return ResponseEntity.ok().body(newEntity)
    }

    @PutMapping("update")
    fun update(@RequestBody product: ProductDTO): ResponseEntity<ProductDTO> {
        val productOpt = repo.findById(product.id)

        if (productOpt.isEmpty)
        {
            return ResponseEntity.notFound().build()
        }

        val updatedProduct = repo.save(product)
        return ResponseEntity.ok().body(updatedProduct)
    }

    @GetMapping("/{id}")
    fun read(@PathVariable id: Long): ResponseEntity<ProductDTO> {
        val productOpt = repo.findById(id)

        if (productOpt.isEmpty)
        {
            return ResponseEntity.notFound().build()
        }

        return ResponseEntity.ok().body(productOpt.get())
    }

    @DeleteMapping("/delete/{id}")
    fun delete(@PathVariable id: Long): ResponseEntity<Unit> {
        val productOpt = repo.findById(id)

        if (productOpt.isEmpty)
        {
            return ResponseEntity.notFound().build()
        }

        repo.delete(productOpt.get())
        return ResponseEntity.ok().build()
    }
}