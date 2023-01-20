package org.uninstal.client.connection.impl;

public enum ClientResourcesResult {
  VALID, // Успешная проверка.
  UPDATE, // Необходимо обновление файлов.
  BAD // Клиент игрока содержит сторонние файлы.
}