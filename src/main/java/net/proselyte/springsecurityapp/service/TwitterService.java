package net.proselyte.springsecurityapp.service;

import twitter4j.auth.AccessToken;

public interface TwitterService {
    String getAuthURL();
    AccessToken getAccessToken(String oauth_verifier);
}
