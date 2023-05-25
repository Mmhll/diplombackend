package com.mhl.mycompanybackend

import io.swagger.v3.oas.annotations.OpenAPIDefinition
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
@OpenAPIDefinition
open class MycompanybackendApplication
    fun main(args: Array<String>) {
        runApplication<MycompanybackendApplication>(*args)
    }

