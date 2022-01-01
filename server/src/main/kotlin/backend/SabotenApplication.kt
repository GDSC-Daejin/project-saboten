package backend

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import common.entities.User

@SpringBootApplication
class SabotenApplication

fun main(args: Array<String>) {
    val user = User(0, "", "")
    runApplication<SabotenApplication>(*args)
}

