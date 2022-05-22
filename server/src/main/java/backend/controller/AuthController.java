package backend.controller;

import backend.controller.annotation.Version1RestController;
import backend.service.AuthService;
import common.message.UserResponseMessage;
import common.model.request.auth.TokenReissueRequest;
import common.model.request.user.UserLoginTestRequest;
import common.model.request.user.UserSignUpRequest;
import common.model.reseponse.ApiResponse;
import common.model.reseponse.auth.JwtTokenResponse;
import common.model.reseponse.user.UserResponse;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Version1RestController
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    private final String authUrl = "/auth";

    // 현재 auth 기능들은 제대로 된 auth 가 아니라서 추후 소셜로그인 구현이 되면 제대로 구현합시다!
    @ApiOperation(value = "유저 회원가입", notes = "임시 회원가입입니다 추후 소셜로그인 대응해야 합니다.")
    @PostMapping(authUrl + "/signup")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<UserResponse> signup(@RequestBody UserSignUpRequest userSignInRequest) {
        UserResponse userResponse = authService.signup(userSignInRequest);
        return ApiResponse.withMessage(userResponse, UserResponseMessage.USER_CREATED);
    }

    @ApiOperation(value = "유저 로그인", notes = "임시 로그인입니다 추후 소셜로그인 대응해야 합니다.")
    @PostMapping(authUrl + "/login")
    public ApiResponse<JwtTokenResponse> login(@RequestBody UserLoginTestRequest userLoginTestRequest) {
        JwtTokenResponse jwtTokenResponse = authService.login(userLoginTestRequest);
        return ApiResponse.withMessage(jwtTokenResponse, UserResponseMessage.USER_LOGIN);
    }

    @ApiOperation(value = "Access Token 재발급", notes = "Access Token 만료 시 Refresh Token을 가지고 재발급을 합니다.")
    @PostMapping(authUrl + "/reissue")
    public ApiResponse<JwtTokenResponse> reissue(@RequestBody TokenReissueRequest tokenReissueRequest) {
        JwtTokenResponse jwtTokenResponse = authService.reissue(tokenReissueRequest);
        return ApiResponse.withMessage(jwtTokenResponse, UserResponseMessage.USER_TOKEN_REISSUE);
    }

    @ApiOperation(value = "유저 로그아웃", notes = "Access Token, Refresh Token 제거 합니다 (클라이언트 측에서도 헤더를 제거해줘야 합니다.)")
    @DeleteMapping(authUrl + "/logout")
    public ApiResponse<?> logout(@RequestBody TokenReissueRequest tokenReissueRequest) {
        authService.logout(tokenReissueRequest.getAccessToken(), tokenReissueRequest.getRefreshToken());
        return ApiResponse.withMessage(null, UserResponseMessage.USER_LOGOUT);
    }
}
