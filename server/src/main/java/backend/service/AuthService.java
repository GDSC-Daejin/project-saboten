package backend.service;

import backend.exception.ApiException;
import backend.jwt.RoleType;
import backend.jwt.TokenProvider;
import backend.model.user.RefreshTokenEntity;
import backend.model.user.UserEntity;
import backend.repository.user.RefreshTokenRepository;
import backend.repository.user.UserRepository;
import common.message.UserResponseMessage;
import common.model.request.user.UserLoginTestRequest;
import common.model.request.user.UserSignInRequest;
import common.model.reseponse.ApiResponse;
import common.model.reseponse.user.User;
import common.model.reseponse.auth.JwtToken;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final UserRepository userRepository;
    private final TokenProvider tokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;
    private final UserService userService;

    @Transactional
    public User signup(UserSignInRequest userSignInRequest) {
        if (userRepository.existsByNickname(userSignInRequest.getNickname())) {
            throw new ApiException(UserResponseMessage.USER_ALREADY_REGISTERED);
        }

        UserEntity user = new UserEntity(userSignInRequest);
        return userRepository.save(user).toDto();
    }

    public JwtToken login(UserLoginTestRequest userLoginTestRequest) {
        // 테스트용
        Long id = userLoginTestRequest.getId();

        // 보류 이유 : 소셜로그인 기반이라 필요없음 1. Login ID/PW 를 기반으로 AuthenticationToken 생성
        // 보류 이유 : 소셜로그인 기반이라 필요없음 2. 실제로 검증 (사용자 비밀번호 체크) 이 이루어지는 부분

        // 해당 유저가 있는지 검증
        UserEntity user = userService.findUserEntity(id);

        // 3. 인증 정보를 기반으로 JWT 토큰 생성
        JwtToken jwtToken = tokenProvider.generateJwtToken(Long.toString(id), RoleType.USER);

        // 4. RefreshToken 저장
        RefreshTokenEntity refreshToken = RefreshTokenEntity.builder()
                .user(user)
                .refreshToken(jwtToken.getRefreshToken())
                .build();

        refreshTokenRepository.save(refreshToken);

        // 5. 토큰 발급
        return jwtToken;
    }
}
