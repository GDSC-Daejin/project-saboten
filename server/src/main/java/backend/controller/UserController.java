package backend.controller;

import common.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
class UserController {

    @GetMapping("/api/users/{id}")
    public ResponseEntity<User> getUser() {
        return ResponseEntity.ok(new User(0L, "", ""));
    }

}