package backend.oauth.info;

import backend.exception.ApiException;
import backend.oauth.entity.ProviderType;
import backend.oauth.info.impl.GoogleOAuth2UserInfo;
import common.message.UserResponseMessage;

import java.util.Map;

public class OAuth2UserInfoFactory {
    public static OAuth2UserInfo getOAuth2UserInfo(ProviderType providerType, Map<String, Object> attributes) {
        switch (providerType) {
            case GOOGLE : return new GoogleOAuth2UserInfo(attributes);
            default : throw new IllegalArgumentException("Invalid Provider Type.");
        }
    }
}
