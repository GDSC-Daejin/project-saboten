package backend.oauth.handler;

import backend.jwt.RoleType;
import backend.jwt.TokenProvider;
import backend.model.user.RefreshTokenEntity;
import backend.model.user.UserEntity;
import backend.oauth.entity.ProviderType;
import backend.oauth.info.OAuth2UserInfo;
import backend.oauth.info.OAuth2UserInfoFactory;
import backend.repository.user.RefreshTokenRepository;
import backend.service.AuthService;
import backend.service.UserService;
import backend.util.CookieUtil;
import common.model.reseponse.auth.JwtTokenResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

import static backend.oauth.repository.OAuth2AuthorizationRequestBasedOnCookieRepository.REDIRECT_URI_PARAM_COOKIE_NAME;

@Component
@RequiredArgsConstructor
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final TokenProvider tokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;
    private final UserService userService;
    private final AuthService authService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        String targetUrl = determineTargetUrl(request, response, authentication);

        if (response.isCommitted()) {
            logger.debug("Response has already been committed. Unable to redirect to " + targetUrl);
            return;
        }

        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }

    protected String determineTargetUrl(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        Optional<String> redirectUri = CookieUtil.getCookie(request, REDIRECT_URI_PARAM_COOKIE_NAME)
                .map(Cookie::getValue);

        if(redirectUri.isPresent()) {
            throw new IllegalArgumentException("Sorry! We've got an Unauthorized Redirect URI and can't proceed with the authentication");
        }

        String targetUrl = redirectUri.orElse(getDefaultTargetUrl());

        OAuth2AuthenticationToken authToken = (OAuth2AuthenticationToken) authentication;
        ProviderType providerType = ProviderType.valueOf(authToken.getAuthorizedClientRegistrationId().toUpperCase());

        DefaultOAuth2User user = ((DefaultOAuth2User) authentication.getPrincipal());
        OAuth2UserInfo userInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(providerType, user.getAttributes());

        JwtTokenResponse jwtTokenResponse  = createToken(userInfo.getId());
        String accessToken = jwtTokenResponse.getAccessToken();
        String refreshToken = jwtTokenResponse.getRefreshToken();

        int cookieMaxAge = tokenProvider.getExpiration(refreshToken).intValue() / 60;

        CookieUtil.deleteCookie(request, response, "refresh_token");
        CookieUtil.addCookie(response, "refresh_token", refreshToken, cookieMaxAge);

        return UriComponentsBuilder.fromUriString(targetUrl)
                .queryParam("token", accessToken)
                .build().toUriString();
    }

    private JwtTokenResponse createToken(String id) {
        UserEntity user = userService.findUserEntityByNickname(id);
        JwtTokenResponse jwtTokenResponse = tokenProvider.generateJwtToken(id, RoleType.USER);
        // DB 저장
        RefreshTokenEntity refreshToken = RefreshTokenEntity.builder()
                .user(user)
                .refreshToken(jwtTokenResponse.getRefreshToken())
                .build();
        refreshTokenRepository.save(refreshToken);

        return jwtTokenResponse;
    }
}
