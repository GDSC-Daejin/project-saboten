package backend.controller;

import backend.controller.annotation.Version1RestController;
import backend.jwt.SecurityUtil;
import backend.model.user.UserEntity;
import backend.service.UserService;
import common.model.reseponse.user.User;
import common.model.reseponse.ApiResponse;
import common.message.UserResponseMessage;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@Version1RestController
@RequiredArgsConstructor
class UserController {
    private final UserService userService;

    @ApiOperation(value = "유저정보조회 Api 테스트", notes = "임시 테스트용")
    @GetMapping("/users/{id}")
    public ApiResponse<User> getUser(@PathVariable("id") Long id) {
        return ApiResponse.withMessage(new User(id, "", ""), UserResponseMessage.USER_NOT_REGISTERED);
    }

    // Jwt 잘 작동하는 지 테스트 용도입니다. 근데 이거 좀만 수정해서 제대로 사용하는거 나을듯
    @GetMapping("/users/test")
    public ApiResponse<User> getUserTest() {
        Long userId = SecurityUtil.getCurrentUserId();
        UserEntity userEntity = userService.findUserEntity(userId);
        return ApiResponse.withMessage(userEntity.toDto(), UserResponseMessage.USER_LOGIN);
    }
}