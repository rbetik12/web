package com.web.lab45.lab45

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer

@SpringBootApplication
class Lab45Application : SpringBootServletInitializer()

fun main(args: Array<String>) {
	runApplication<Lab45Application>(*args)
}
