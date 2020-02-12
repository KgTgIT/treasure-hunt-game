package it.kgtg.treasure.hunt

import io.micronaut.runtime.Micronaut
import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.info.Contact
import io.swagger.v3.oas.annotations.info.Info
import io.swagger.v3.oas.annotations.info.License
import org.springframework.boot.autoconfigure.SpringBootApplication

@OpenAPIDefinition(
    info = Info(
        title = "Treasure Hunt Game",
        version = "1.0",
        description = "Implementation of Treasure Hunt Game API",
        license = License(name = "Apache 2.0", url = "https://github.com/KgTgIT/micronaut"),
        contact = Contact(name = "Kamil Gunia", email = "kgtg.it@outlook.com")
    )
)
@SpringBootApplication
class Application

fun main(args: Array<String>) {
    Micronaut.build(*args)
        .packages("it.kgtg.treasure.hunt")
        .mainClass(Application::class.java)
        .start()
}
