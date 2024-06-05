package com.fplke.msauthentication.utils;

public class Constants {
    private Constants(){}

    public static class Strings{
        public static final String INVALID_USERNAME = "Username should contain alphanumeric characters, spaces and underscores only";
        public static final String INVALID_PASSWORD = "Password should be at least 8 characters, at most 100 characters";
    }

    public static class Roles{
        public static final String ROLE_USER = "ROLE_USER";
        public static final String ROLE_ADMIN = "ROLE_ADMIN";
    }
}
