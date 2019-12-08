package de.openmail

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan

@ComponentScan("de.openmail.repository", "de.openmail.model", "de.openmail.config", "de.openmail.controller")
@SpringBootApplication
open class Openmail

fun main(args: Array<String>) {
    runApplication<Openmail>(*args)

}

