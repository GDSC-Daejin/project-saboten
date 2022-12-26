package backend.controller;

import backend.controller.annotation.Version1RestController;
import backend.controller.dto.UserDto;
import backend.controller.swagger.response.UnauthorizedResponse;
import backend.controller.swagger.response.UserNotFoundResponse;
import backend.jwt.SecurityUtil;
import backend.service.UserService;
import common.model.reseponse.user.UserInfoResponse;
import common.model.reseponse.ApiResponse;
import common.message.UserResponseMessage;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Version1RestController
@RequiredArgsConstructor
class UserController {
    private final UserService userService;

    // TODO : UserService든 util로 뺴버리기
    private UserDto getUserEntity() {
        Long userId = SecurityUtil.getCurrentUserId();

        if(userId != null)
            return userService.findUserEntity(userId);

        return null;
    }

    @ApiOperation(value = "로그인한 사용자의 프로필 상세정보 조회", notes = "로그인한 사용자 자기자신의 프로필을 조회합니다.")
    @ApiResponses({
            @io.swagger.annotations.ApiResponse(code = 401, message = "", response = UnauthorizedResponse.class)
    })
    @GetMapping("/userInfo")
    public ApiResponse<UserInfoResponse> getUserInfo() {
        UserDto userDto = getUserEntity();
        return ApiResponse.withMessage(userDto.toInfoResponse(),UserResponseMessage.USER_READ);
    }

    @ApiOperation(value = "특정 사용자 프로필 조회", notes = "특정 사용자 프로필을 조회합니다.")
    @ApiResponses({
            @io.swagger.annotations.ApiResponse(code = 404, message = "", response = UserNotFoundResponse.class)
    })
    @GetMapping("/userInfo/{id}")
    public ApiResponse<UserInfoResponse> getOtherUserInfo(@PathVariable Long id) {
        UserDto userDto= userService.findUserEntity(id);
        return ApiResponse.withMessage(userDto.toInfoResponse(),UserResponseMessage.USER_READ);
    }
}