package com.personal.devetblogapi.constant;

public final class AppEndpoint {

  public static final String ROOT_V1 = "/api/v1/";
  public static final String[] PUBLIC_LIST_GET = {
    "/api/v1/articles", "/api/v1/articles/**", "/api/v1/users/*/articles"
  };
  public static final String[] PUBLIC_LIST = {
    "/api/v1/auth/**",
    "/api/v1/files/images/**",
    "/api/v1/files/pdf/**",
    "/api/v1/files/data/**",
    "/swagger-ui/**",
    "/v3/api-docs/**",
    "/actuator/**"
  };
  public static final String[] ADMIN_LIST = {"/swagger-ui/**", "/v3/api-docs/**", "/actuator/**"};

  public static final class Auth {
    public static final String BASE = ROOT_V1 + "auth/";
    public static final String REGISTER = "register";
    public static final String LOGIN = "login";
    public static final String LOGOUT = "logout";
  }

  public static final class Article {
    private static final String BASE = "articles";

    public static final String LIST_ALL = BASE;
    public static final String LIST_ALL_BY_USER_ID = "/users/{id}/" + BASE;
    public static final String GET_BY_ID = BASE + "/{id}";
    public static final String CREATE = BASE;
    public static final String UPDATE = BASE + "/{id}";
    public static final String DELETE = BASE + "/{id}";
    public static final String SEARCH = BASE + "/search";
  }

  public static final class User {
    private static final String BASE = "users";

    public static final String LIST_ALL = BASE;
    public static final String GET_BY_ID = BASE + "/{id}";
    public static final String CREATE = BASE;
    public static final String UPDATE = BASE + "/{id}";
    public static final String DELETE = BASE + "/{id}";
  }

  public static final class File {
    private static final String BASE = "files";
    public static final String GET_ALL = BASE;
    public static final String GET_BY_ID = BASE + "/{id}";
    public static final String UPLOAD = BASE;
    public static final String DELETE_BY_ID = BASE + "/{id}";
    public static final String DELETE_BY_URL = BASE + "/{url}";
  }
}
