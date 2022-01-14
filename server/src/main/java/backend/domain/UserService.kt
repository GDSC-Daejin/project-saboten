package backend.domain

import backend.data.UserRepository
import common.model.User
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service

@Service
class UserService(private val userRepository: UserRepository) {

    suspend fun findMe(): ResponseEntity<User> = userRepository
        .runCatching { findMe() }
        .fold({ me ->
            if (me == null) ResponseEntity.notFound().build()
            else ResponseEntity.ok(me)
        }, { throwable ->
            /* userRepository.findMe() 에서 Exception 이 throw 되면 이쪽으로 옵니다. */
            ResponseEntity.notFound().build()
        })


}