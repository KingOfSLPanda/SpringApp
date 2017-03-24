package net.proselyte.springsecurityapp.service;

/**
 * Service for Security.
 */

public interface SecurityService {

    String findLoggedInUsername();

    void autoLogin(String username, String password);

    void clearAuth();
}
