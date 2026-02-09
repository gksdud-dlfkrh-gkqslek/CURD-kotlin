package com.bs.crudkotlin

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class CrudKotlinApplication

fun main(args: Array<String>) {
    runApplication<CrudKotlinApplication>(*args)
}
