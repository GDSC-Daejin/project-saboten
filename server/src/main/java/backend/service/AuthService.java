package backend.service;

import backend.exception.ApiException;
import backend.jwt.JwtToken;
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
import lombok.RequiredArgsConstructor;
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

        // 1. Login ID/PW 를 기반으로 AuthenticationToken 생성
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(id, 123);

        // 2. 실제로 검증 (사용자 비밀번호 체크) 이 이루어지는 부분
        //    authenticate 메서드가 실행이 될 때 CustomUserDetailsService 에서 만들었던 loadUserByUsername 메서드가 실행됨
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        // 3. 인증 정보를 기반으로 JWT 토큰 생성
        JwtToken jwtToken = tokenProvider.generateJwtToken(authentication);

        // 4. RefreshToken 저장
        RefreshTokenEntity refreshToken = RefreshTokenEntity.builder()
                .userId(Long.parseLong(authentication.getName()))
                .refreshToken(jwtToken.getRefreshToken())
                .build();

        refreshTokenRepository.save(refreshToken);

        // 5. 토큰 발급
        return jwtToken;
    }
}
