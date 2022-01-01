package backend

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SabotenApplication

fun main(args: Array<String>) {
    runApplication<SabotenApplication>(*args)
}

