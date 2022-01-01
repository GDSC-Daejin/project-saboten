package backend

import backend.user.UserHandler
import backend.user.users
import org.springframework.web.reactive.function.server.coRouter

object Routes {

    fun router(
        userHandler: UserHandler
    ) = coRouter {
        "/api".nest {

            users(userHandler)


        }
    }

}