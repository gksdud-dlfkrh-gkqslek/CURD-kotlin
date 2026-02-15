package com.bs.crudkotlin

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@SpringBootApplication
@EnableJpaAuditing
class CrudKotlinApplication

fun main(args: Array<String>) {
    runApplication<CrudKotlinApplication>(*args)
}
