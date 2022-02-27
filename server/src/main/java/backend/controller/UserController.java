package backend.controller;

import common.model.User;
import common.model.reseponse.ApiResponse;
import common.model.reseponse.message.UserResponseMessage;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
class UserController {
    @ApiOperation(value = "유저정보조회 Api 테스트", notes = "임시 테스트용")
    @GetMapping("/api/users/{id}")
    public ApiResponse<User> getUser(@PathVariable("id") Long id) {
        return ApiResponse.withMessage(new User(id, "", ""), UserResponseMessage.USER_NOT_REGISTERED);
    }
}