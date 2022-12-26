package backend.service;

import backend.controller.dto.UserDto;
import backend.exception.ApiException;
import backend.jwt.RedisUtil;
import backend.jwt.RoleType;
import backend.jwt.SecurityUtil;
import backend.jwt.TokenProvider;
import backend.model.user.RefreshTokenEntity;
import backend.model.user.UserEntity;
import backend.repository.user.RefreshTokenRepository;
import backend.repository.user.UserRepository;
import common.message.BasicResponseMessage;
import common.message.UserResponseMessage;
import common.model.request.auth.TokenReissueRequest;
import common.model.request.user.UserLoginTestRequest;
import common.model.request.user.UserSignUpRequest;
import common.model.reseponse.user.UserInfoResponse;
import common.model.reseponse.user.UserResponse;
import common.model.reseponse.auth.JwtTokenResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final TokenProvider tokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;
    private final UserService userService;
    private final RedisUtil redisUtil;

    @Transactional
    public UserResponse signup(UserSignUpRequest userSignInRequest) {
        if (userRepository.existsByNickname(userSignInRequest.getNickname())) {
            throw new ApiException(UserResponseMessage.USER_ALREADY_REGISTERED);
        }

        UserEntity user = new UserEntity(userSignInRequest);
        return userRepository.save(user).toDto().toResponse();
    }

    @Transactional
    public JwtTokenResponse login(UserLoginTestRequest userLoginTestRequest) {
        // 테스트용
        Long id = userLoginTestRequest.getId();

        // 보류 이유 : 소셜로그인 기반이라 필요없음 1. Login ID/PW 를 기반으로 AuthenticationToken 생성
        // 보류 이유 : 소셜로그인 기반이라 필요없음 2. 실제로 검증 (사용자 비밀번호 체크) 이 이루어지는 부분

        // 해당 유저가 있는지 검증
        UserDto user = userService.findUserEntity(id);

        // 3. 인증 정보를 기반으로 JWT 토큰 생성
        JwtTokenResponse jwtTokenResponse = tokenProvider.generateJwtToken(Long.toString(id), RoleType.USER);

        // 4. RefreshToken 저장
        RefreshTokenEntity refreshToken = RefreshTokenEntity.builder()
                .user(user.toEntity())
                .refreshToken(jwtTokenResponse.getRefreshToken())
                .build();

        refreshTokenRepository.save(refreshToken);

        // 5. 토큰 발급
        return jwtTokenResponse;
    }

    @Transactional
    public JwtTokenResponse socialLogin(UserLoginTestRequest userLoginTestRequest) {
        //소셜로그인 테스트용
        Long id = userLoginTestRequest.getId();
        UserDto user = userService.findUserEntity(id);
        return tokenProvider.generateJwtToken(Long.toString(id), RoleType.USER);
    }

    @Transactional
    public JwtTokenResponse reissue(TokenReissueRequest tokenReissueRequest) {
        // 1. Refresh Token 검증
        if (!tokenProvider.validateToken(tokenReissueRequest.getRefreshToken())) {
            throw new ApiException(BasicResponseMessage.INVALID_REFRESH_TOKEN);
        }

        // 2. Access Token 에서 Member ID 가져오기
        Authentication authentication = tokenProvider.getAuthentication(tokenReissueRequest.getAccessToken());

        // 3. 저장소에서 Member ID 를 기반으로 Refresh Token 값 가져옴
        // TODO : 지금 UserFind 하는 것이 중복적으로 코드가 발생하는데 Controller Layer 에서 userService를 이용하여
        // authService에 전달하는게 좋을듯?? (Entity가 mapsId 때문에 이렇긴한데....)
        // 논의가 필요!!!!
        String id = authentication.getName();
        Optional<UserEntity> userEntity = userRepository.findById(Long.parseLong(id));
        if(userEntity.isEmpty())
            throw new ApiException(UserResponseMessage.USER_NOT_FOUND);

        RefreshTokenEntity refreshToken = refreshTokenRepository.findByUser(userEntity.get());

        // 4. Refresh Token 일치하는지 검사
        if (!refreshToken.getRefreshToken().equals(tokenReissueRequest.getRefreshToken())) {
            throw new ApiException(BasicResponseMessage.INVALID_REFRESH_TOKEN);
        }

        // 5. 새로운 토큰 생성
        JwtTokenResponse newRefreshToken = tokenProvider.generateJwtToken(id, RoleType.USER);

        // 6. 저장소 정보 업데이트
        refreshToken.setRefreshToken(newRefreshToken.getRefreshToken());
        refreshTokenRepository.save(refreshToken);

        // 토큰 발급
        return newRefreshToken;
    }

    @Transactional
    public void logout(String accessToken, String refreshToken) {
        // 1. Access Token 검증
        if (!tokenProvider.validateToken(accessToken)) {
            throw new ApiException(BasicResponseMessage.UNAUTHORIZED);
        }

        // 2. Access Token 에서 authentication 을 가져옵니다.
        Authentication authentication = tokenProvider.getAuthentication(accessToken);

        // 3. DB에 저장된 Refresh Token 제거
        Long userId = Long.parseLong(authentication.getName());
        refreshTokenRepository.deleteById(userId);

        // 4. Access Token blacklist에 등록하여 만료시키기
        Long expiration = tokenProvider.getExpiration(accessToken);
        redisUtil.setBlackList(accessToken, "access_token", expiration);
    }

    @Transactional
    public UserInfoResponse signupUpdate(UserInfoResponse userInfoResponse) {
        Long id = SecurityUtil.getCurrentUserId();
        Optional<UserEntity> userEntity = userRepository.findById(id);

        if(userEntity.isEmpty())
            throw new ApiException(UserResponseMessage.USER_NOT_FOUND);

        userEntity.get().setGender(userInfoResponse.getGender().getValue());
        userEntity.get().setNickname(userInfoResponse.getNickname());
        userEntity.get().setMyPageIntroduction(userInfoResponse.getIntroduction());
        userEntity.get().setAge(userInfoResponse.getAge());
        userEntity.get().setEmail(userInfoResponse.getEmail());
        userEntity.get().setUserImage(userInfoResponse.getProfilePhotoUrl());

        return userRepository.save(userEntity.get()).toDto().toInfoResponse();
    }
}
