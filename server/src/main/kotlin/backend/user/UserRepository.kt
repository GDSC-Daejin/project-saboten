package backend.user

import org.springframework.stereotype.Repository
import common.entities.User

@Repository
class UserRepository {

//    fun findUser(id : Long) : User = User(
//        id,
//        "Harry",
//        ""
//    )

    fun findUser(id : Long) : String = "Harry"

}