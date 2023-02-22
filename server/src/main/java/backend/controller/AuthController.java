package backend.controller;

import backend.controller.annotation.Version1RestController;
import backend.controller.swagger.response.SocialInvalidLoginResponse;
import backend.jwt.RoleType;
import backend.jwt.TokenProvider;
import backend.service.AuthService;
import common.message.UserResponseMessage;
import common.model.request.auth.SocialLoginRequest;
import common.model.request.auth.TokenReissueRequest;
import common.model.request.user.UserSignUpRequest;
import common.model.reseponse.ApiResponse;
import common.model.reseponse.auth.JwtTokenResponse;
import common.model.reseponse.user.UserInfoResponse;
import common.model.reseponse.user.UserResponse;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

// TODO : 불필요한 API 제거하기.
@Version1RestController
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    private final String authUrl = "/auth";


    //이름 고민중. signupUpdate() / userInfoUpdate()
    @ApiOperation(value = "유저 회원가입 추가정보등록", notes = "소셜로그인 후 추가정보 등록기능을 수행합니다.")
    @PutMapping(authUrl+"/signupUpdate")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<UserInfoResponse> signupUpdate(@RequestBody UserInfoResponse userInfoResponse){
        UserInfoResponse userInfo = authService.signupUpdate(userInfoResponse);
        return ApiResponse.withMessage(userInfoResponse, UserResponseMessage.USER_UPDATED);
    }


    // 현재 auth 기능들은 제대로 된 auth 가 아니라서 추후 소셜로그인 구현이 되면 제대로 구현합시다!
    @ApiOperation(value = "유저 회원가입", notes = "임시 회원가입입니다 추후 소셜로그인 대응해야 합니다.")
    @PostMapping(authUrl + "/signup")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<UserResponse> signup(@RequestBody UserSignUpRequest userSignInRequest) {
        UserResponse userResponse = authService.signup(userSignInRequest);
        return ApiResponse.withMessage(userResponse, UserResponseMessage.USER_CREATED);
    }

    @ApiOperation(value = "구글 소셜 로그인 (안드로이드 환경)", notes = "구글 소셜 로그인 사용방법\n" +
            "1. 안드로이드 측에서 구글 로그인을 한다.\n" +
            "2. 로그인 성공 이후 얻은 AccessToken을 백엔드 API body에 넣어 요청한다.\n" +
            "3. 선인장 백엔드에서도 로그인 성공!!"
    )
    @ApiResponses({
            @io.swagger.annotations.ApiResponse(code = 401, message = "", response = SocialInvalidLoginResponse.class)
    })
    @PostMapping(authUrl + "/social/login/google")
    public ApiResponse<JwtTokenResponse> googleLogin(@RequestBody SocialLoginRequest socialLoginRequest) {
        JwtTokenResponse jwtTokenResponse = authService.googleLogin(socialLoginRequest.getToken());
        return ApiResponse.withMessage(jwtTokenResponse, UserResponseMessage.USER_LOGIN);
    }

    @ApiOperation(value = "카카오 소셜 로그인 (안드로이드 환경)", notes = "카카오 소셜 로그인\n" +
            "1. 안드로이드 측에서 카카오 로그인을 한다.\n" +
            "2. 로그인 성공 이후 얻은 AccessToken을 백엔드 API body에 넣어 요청한다.\n" +
            "3. 선인장 백엔드에서도 로그인 성공!!"
    )
    @ApiResponses({
            @io.swagger.annotations.ApiResponse(code = 401, message = "", response = SocialInvalidLoginResponse.class)
    })
    @PostMapping(authUrl + "/social/login/kakao")
    public ApiResponse<JwtTokenResponse> kakaoLogin(@RequestBody SocialLoginRequest socialLoginRequest) {
        JwtTokenResponse jwtTokenResponse = authService.kakaoLogin(socialLoginRequest.getToken());
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
