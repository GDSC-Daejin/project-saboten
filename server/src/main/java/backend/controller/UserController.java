package backend.controller;

import common.model.ApiResponse;
import common.model.User;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
class UserController {
    @ApiOperation(value = "유저정보조회 Api 테스트", notes = "임시 테스트용")
    @GetMapping("/api/users/{id}")
    public ResponseEntity<ApiResponse<User>> getUser(@PathVariable("id") Long id) {
        return ResponseEntity.ok(new ApiResponse(null, new User(id, "", ""), ""));
    }
}