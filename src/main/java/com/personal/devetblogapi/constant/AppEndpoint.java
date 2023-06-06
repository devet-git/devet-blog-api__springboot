package com.personal.devetblogapi.constant;

public final class AppEndpoint {

  private static final String ROOT_V1 = "/api/v1/";

  public static final String[] WHITE_LIST = {
    "/api/v1/auth/**",
    "/api/v1/files/images/**",
    "/api/v1/files/pdf/**",
    "/api/v1/files/data/**",
    "/swagger-ui/**",
    "/v3/api-docs/**"
  };

  public static final class Auth {
    public static final String BASE = ROOT_V1 + "auth/";
    public static final String REGISTER = "register";
    public static final String LOGIN = "login";
    public static final String LOGOUT = "logout";
  }

  public static final class Article {
    public static final String BASE = ROOT_V1 + "articles/";

    public static final String get = "/";
  }
}
