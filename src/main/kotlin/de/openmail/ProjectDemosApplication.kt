package de.openmail

import org.slf4j.LoggerFactory
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan

@ComponentScan("de.openmail.repository", "de.openmail.model", "de.openmail.config", "de.openmail.controller")
@SpringBootApplication
open class ProjectDemosApplication

fun main(args: Array<String>) {
    runApplication<ProjectDemosApplication>(*args)

}
