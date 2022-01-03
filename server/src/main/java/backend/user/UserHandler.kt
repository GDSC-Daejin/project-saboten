package backend.user

import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.bodyValueAndAwait
import org.springframework.web.reactive.function.server.json

@Component
class UserHandler(val userRepository: UserRepository) {

    suspend fun findUser(request: ServerRequest): ServerResponse {
        val user = userRepository.findUser(request.pathVariable("id").toLong())
        return ServerResponse.ok().json().bodyValueAndAwait(user)
    }

}