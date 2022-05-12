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
        UserEntity savedUser = userRepository.findByNickname(userInfo.getId());

        // TODO : Id값이 매우 큰 형태라 String 형태로 관리해야될거같음.......
        //  식별할수 있는 SEQ값(식별 ID) 그리고 id를 저장하는 Id 컬럼 으로 구성해야할듯??
        // UserEntity savedUser = userRepository.findByUserId(Long.parseLong(userInfo.getId()));

        if (savedUser != null) {
            // TODO : 이부분은 Want랑 상의 후 넣을지 확인해야함. (넣어야 될것 같긴함.)
//            if (providerType != savedUser.getProviderType()) {
//                throw new OAuthProviderMissMatchException(
//                        "Looks like you're signed up with " + providerType +
//                                " account. Please use your " + savedUser.getProviderType() + " account to login."
//                );
//            }
        } else {
            // throw new ApiException(UserResponseMessage.USER_NOT_FOUND);
            // savedUser = createUser(userInfo, providerType);
        }
        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint()
                .getUserNameAttributeName();

        return new DefaultOAuth2User(Collections.singletonList(new SimpleGrantedAuthority(RoleType.USER.toString()))
                ,user.getAttributes(), userNameAttributeName);
    }
}
