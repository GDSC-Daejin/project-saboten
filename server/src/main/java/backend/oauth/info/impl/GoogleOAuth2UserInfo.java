package backend.oauth.info.impl;

import java.util.Map;

import backend.oauth.info.OAuth2UserInfo;

public class GoogleOAuth2UserInfo extends OAuth2UserInfo {

    private String id;
    private String name;
    private String email;
    private String imageUrl;

    private GoogleOAuth2UserInfo(Map<String, Object> attributes) {
        super(attributes);
    }

    public GoogleOAuth2UserInfo(String id, String name, String email, String imageUrl) {
        super(null);
        this.id = id;
        this.name = name;
        this.email = email;
        this.imageUrl = imageUrl;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public String getImageUrl() {
        return imageUrl;
    }
}
