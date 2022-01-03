package backend.user

import org.springframework.http.MediaType
import org.springframework.web.reactive.function.server.CoRouterFunctionDsl

fun CoRouterFunctionDsl.users(userHandler: UserHandler) =
    "/users/".nest {
        accept(MediaType.APPLICATION_JSON).nest {
            GET("{id}", userHandler::findUser)
        }
    }