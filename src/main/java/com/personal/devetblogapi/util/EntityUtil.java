package com.personal.devetblogapi.util;

import java.lang.reflect.Field;
import java.util.Map;
import org.springframework.util.ReflectionUtils;

public final class EntityUtil {
  public static <T> void partialUpdate(Object entity, Map<String, T> fields) {
    fields.forEach(
        (key, value) -> {
          Field field = ReflectionUtils.findField(entity.getClass(), key);
          assert field != null;
          field.setAccessible(true);
          ReflectionUtils.setField(field, entity, value);
        });
  }
}
