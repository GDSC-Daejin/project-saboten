package backend.user

import common.entities.User
import org.springframework.stereotype.Repository

@Repository
class UserRepository {

    fun findUser(id : Long) = User(
        id,
        "Harry",
        ""
    )

}