package backend.controller;

import backend.controller.annotation.Version1RestController;
import backend.jwt.SecurityUtil;
import backend.model.user.UserEntity;
import backend.service.UserService;
import common.model.reseponse.user.UserInfoResponse;
import common.model.reseponse.user.UserResponse;
import common.model.reseponse.ApiResponse;
import common.message.UserResponseMessage;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Version1RestController
@RequiredArgsConstructor
class UserController {
    private final UserService userService;

    private UserEntity getUserEntity() {
        Long userId = SecurityUtil.getCurrentUserId();
        if(userId != null)
            return userService.findUserEntity(userId);

        return null;
    }

    @ApiOperation(value = "로그인한 사용자의 프로필 조회", notes = "로그인한 사용자 자기자신의 프로필을 조회합니다.")
    @GetMapping("/userInfo")
    public ApiResponse<UserInfoResponse> getUserInfo() {
        UserEntity userEntity = getUserEntity();
        return ApiResponse.withMessage(userEntity.toUserInfoDTO(),UserResponseMessage.USER_READ);
    }

    @ApiOperation(value = "특정 사용자 프로필 조회", notes = "특정 사용자 프로필을 조회합니다.")
    @GetMapping("/userInfo/{id}")
    public ApiResponse<UserInfoResponse> getOtherUserInfo(@PathVariable Long id) {
        UserEntity userEntity = userService.findUserEntity(id);
        return ApiResponse.withMessage(userEntity.toUserInfoDTO(),UserResponseMessage.USER_READ);
    }
}