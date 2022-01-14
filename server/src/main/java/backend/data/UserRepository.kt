package backend.data

import common.model.User
import org.springframework.stereotype.Repository

@Repository
class UserRepository {

    suspend fun findMe() : User? {
        return null
    }

}