package backend.service;

import backend.exception.ApiException;
import backend.jwt.RedisUtil;
import backend.jwt.RoleType;
import backend.jwt.SecurityUtil;
import backend.jwt.TokenProvider;
import backend.model.user.RefreshTokenEntity;
import backend.model.user.UserEntity;
import backend.oauth.entity.ProviderType;
import backend.oauth.info.OAuth2UserInfo;
import backend.oauth.info.OAuth2UserInfoFactory;
import backend.repository.user.RefreshTokenRepository;
import backend.repository.user.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import common.message.AuthResponseMessage;
import common.message.BasicResponseMessage;
import common.message.UserResponseMessage;
import common.model.request.auth.TokenReissueRequest;
import common.model.request.user.UserSignUpRequest;
import common.model.reseponse.user.UserInfoResponse;
import common.model.reseponse.user.UserResponse;
import common.model.reseponse.auth.JwtTokenResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final TokenProvider tokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
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
    private JwtTokenResponse createToken(UserEntity user) {
        JwtTokenResponse jwtTokenResponse = tokenProvider.generateJwtToken(user.getUserId().toString(), RoleType.USER);

        RefreshTokenEntity refreshToken = RefreshTokenEntity.builder()
                .user(user)
                .refreshToken(jwtTokenResponse.getRefreshToken())
                .build();

        refreshTokenRepository.save(refreshToken);

        return jwtTokenResponse;
    }

    @Transactional
    private UserEntity createUser(OAuth2UserInfo userInfo, ProviderType providerType) {
        UserEntity user = UserEntity.builder()
                .socialId(userInfo.getId())
                .providerType(providerType)
                .nickname(userInfo.getName())
                .email(userInfo.getEmail())
                .userImage(userInfo.getImageUrl())
                .build();

        return userRepository.save(user);
    }

    private UserEntity getSocialUser(String body, ProviderType providerType) {
        try {
            Map<String, Object> attributes = objectMapper.readValue(body, Map.class);
            OAuth2UserInfo userInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(providerType , attributes);

            UserEntity user = userRepository.findBySocialId(userInfo.getId());
            if(user == null) {
                user = createUser(userInfo, providerType);
            }
            return user;
        }
        catch (Exception e) {
            throw new ApiException(BasicResponseMessage.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional
    public JwtTokenResponse kakaoLogin(String accessToken) {
        final String KAKAO_USERINFO_URL = "https://kapi.kakao.com/v2/user/me";

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);

        HttpEntity<String> entity = new HttpEntity<>("", headers);
        try {
            HttpEntity<String> response = restTemplate.exchange(KAKAO_USERINFO_URL, HttpMethod.GET , entity, String.class);
            UserEntity user = getSocialUser(response.getBody(), ProviderType.KAKAO);
            return createToken(user);
        }
        catch (HttpClientErrorException e) {
            throw new ApiException(AuthResponseMessage.INVALID_ACCESS_TOKEN);
        }
    }

    @Transactional
    public JwtTokenResponse googleLogin(String accessToken) {
        final String GOOGLE_USERINFO_URL = "https://www.googleapis.com/oauth2/v3/userinfo";

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);

        HttpEntity<String> entity = new HttpEntity<>("", headers);
        try {
            HttpEntity<String> response = restTemplate.exchange(GOOGLE_USERINFO_URL, HttpMethod.GET , entity, String.class);
            UserEntity user = getSocialUser(response.getBody(), ProviderType.GOOGLE);
            return createToken(user);
        }
        catch (HttpClientErrorException e) {
            throw new ApiException(AuthResponseMessage.INVALID_ACCESS_TOKEN);
        }
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

        userEntity.get().setNickname(userInfoResponse.getNickname());
        userEntity.get().setEmail(userInfoResponse.getEmail());
        userEntity.get().setUserImage(userInfoResponse.getProfilePhotoUrl());

        return userRepository.save(userEntity.get()).toDto().toInfoResponse();
    }
}
