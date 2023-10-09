package com.rbetik12.lab1

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer


@SpringBootApplication
class Lab1Application : SpringBootServletInitializer()

fun main(args: Array<String>) {
	runApplication<Lab1Application>(*args)
}
