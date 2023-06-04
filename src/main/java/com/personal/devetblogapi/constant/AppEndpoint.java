package com.personal.devetblogapi.constant;

public final class AppEndpoint {
  private static final String ROOT_V1 = "/api/v1/";

  public static final class Auth {
    public static final String BASE = ROOT_V1 + "auth/";
    public static final String LOGIN = "login";
  }

  public static final class Article {
    public static final String BASE = ROOT_V1 + "articles/";

    public static final String get = "/";
  }
}
