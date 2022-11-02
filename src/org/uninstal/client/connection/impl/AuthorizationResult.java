package org.uninstal.client.connection.impl;

public enum AuthorizationResult {
  SUCCESS("Успешная авторизация"),
  DENY_LOGIN("Пользователь с данным ником не найден"), 
  DENY_PASSWORD("Пароль неверный"),
  TOO_MANY_ATTEMPTS("Слишком много попыток авторизации");

  private final String reason;

  AuthorizationResult(String reason) {
    this.reason = reason;
  }
  
  public String getReason() {
    return reason;
  }
}