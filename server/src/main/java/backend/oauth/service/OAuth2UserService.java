package backend.oauth.service;

import backend.exception.ApiException;
import backend.jwt.RoleType;
import backend.model.user.UserEntity;
import backend.oauth.entity.ProviderType;
import backend.oauth.info.OAuth2UserInfo;
import backend.oauth.info.OAuth2UserInfoFactory;
import backend.repository.user.UserRepository;
import common.message.UserResponseMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import java.util.Collections;

@Service
@RequiredArgsConstructor
public class OAuth2UserService extends DefaultOAuth2UserService {
    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User user = super.loadUser(userRequest);

        try {
            return this.process(userRequest, user);
        } catch (AuthenticationException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            throw new InternalAuthenticationServiceException(e.getMessage(), e.getCause());
        }
    }

    private OAuth2User process(OAuth2UserRequest userRequest, OAuth2User user) {
        ProviderType providerType = ProviderType.valueOf(userRequest.getClientRegistration().getRegistrationId().toUpperCase());

        OAuth2UserInfo userInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(providerType, user.getAttributes());
        UserEntity savedUser = userRepository.findBySocialId(userInfo.getId());

        if (savedUser != null) {
            // TODO : 에러 형식 Response 보내게끔 하기
            if (providerType != savedUser.getProviderType()) {
                throw new ApiException(UserResponseMessage.USER_MISMATCH_PROVIDER_TYPE);
            }
        } else {
            savedUser = createUser(userInfo, providerType);
        }

        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint()
                .getUserNameAttributeName();

        return new DefaultOAuth2User(Collections.singletonList(new SimpleGrantedAuthority(RoleType.USER.toString()))
                ,user.getAttributes(), userNameAttributeName);
    }

    // 로직 테스트 못해봄.
    private UserEntity createUser(OAuth2UserInfo userInfo, ProviderType providerType) {
        // 추후 Entitiy가 변경되어 이메일이나 imageUrl 넣읋수있음 넣기
        UserEntity user = UserEntity.builder()
                .socialId(userInfo.getId())
                .providerType(providerType)
                .nickname(userInfo.getName())
                .email(userInfo.getEmail())
                .userImage(userInfo.getImageUrl())
                .build();

        return userRepository.save(user);
    }
}
