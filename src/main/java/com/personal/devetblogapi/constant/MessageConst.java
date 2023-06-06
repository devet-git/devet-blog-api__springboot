package com.personal.devetblogapi.constant;

public final class MessageConst {
  public static final String SUCCESS = "Success";
  public static final String SERVER_ERROR = "Server error";
  public static final String NOT_AUTHENTICATED = "Access Denied - User not authenticated";

  public static String success(String processName) {
    processName = processName == null ? "Process" : processName;
    return String.format("%s successfully", processName);
  }

  public static String existed(String objectName) {
    return String.format("%s already exist", objectName);
  }

  public static String notExist(String objectName) {
    return String.format("%s is not exist", objectName);
  }

  public static String accessDenied(String objectName) {
    return String.format("Cannot access this %s", objectName);
  }
}
