package backend.controller

import backend.domain.UserService
import common.model.User
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

@Controller
class UserController(private val userService: UserService) {

    @GetMapping("users/me")
    suspend fun getMe(): ResponseEntity<User> {
        return userService.findMe()
    }

}