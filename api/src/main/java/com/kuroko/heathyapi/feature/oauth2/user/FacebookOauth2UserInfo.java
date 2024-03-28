package com.kuroko.heathyapi.feature.oauth2.user;

import java.util.Map;

public class FacebookOauth2UserInfo extends OAuth2UserInfo {

    public FacebookOauth2UserInfo(Map<String, Object> attributes) {
        super(attributes);
    }

    @Override
    public String getId() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getId'");
    }

    @Override
    public String getName() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getName'");
    }

    @Override
    public String getEmail() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getEmail'");
    }

    @Override
    public String getImageUrl() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getImageUrl'");
    }

}
