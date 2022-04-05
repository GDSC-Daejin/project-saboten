package backend.controller;

import backend.controller.annotation.Version1RestController;
import backend.service.AuthService;
import common.message.UserResponseMessage;
import common.model.request.auth.TokenReissueRequest;
import common.model.request.user.UserLoginTestRequest;
import common.model.request.user.UserSignUpRequest;
import common.model.reseponse.ApiResponse;
import common.model.reseponse.auth.JwtToken;
import common.model.reseponse.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Version1RestController
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    private final String authUrl = "/auth";

    // 현재 auth 기능들은 제대로 된 auth 가 아니라서 추후 소셜로그인 구현이 되면 제대로 구현합시다!
    @PostMapping(authUrl + "/signup")
    public ApiResponse<User> signup(@RequestBody UserSignUpRequest userSignInRequest) {
        User user = authService.signup(userSignInRequest);
        return ApiResponse.withMessage(user, UserResponseMessage.USER_CREATED);
    }

    @PostMapping(authUrl + "/login")
    public ApiResponse<JwtToken> login(@RequestBody UserLoginTestRequest userLoginTestRequest) {
        JwtToken jwtToken = authService.login(userLoginTestRequest);
        return ApiResponse.withMessage(jwtToken, UserResponseMessage.USER_LOGIN);
    }

    // 소셜로그인 부분
//    @PostMapping(authUrl + "/login/kakao")
//    public ApiResponse<JwtToken> loginByKakao(@RequestBody MemberRequestDto memberRequestDto) {
//
//    }

    @PostMapping(authUrl + "/reissue")
    public ApiResponse<JwtToken> reissue(@RequestBody TokenReissueRequest tokenReissueRequest) {
        JwtToken jwtToken = authService.reissue(tokenReissueRequest);
        return ApiResponse.withMessage(jwtToken, UserResponseMessage.USER_TOKEN_REISSUE);
    }

    @DeleteMapping(authUrl + "/logout")
    public ApiResponse<?> logout(@RequestBody TokenReissueRequest tokenReissueRequest) {
        authService.logout(tokenReissueRequest.getAccessToken(), tokenReissueRequest.getRefreshToken());
        return ApiResponse.withMessage(null, UserResponseMessage.USER_LOGOUT);
    }
}
