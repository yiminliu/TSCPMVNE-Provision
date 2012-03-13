package com.tscp.util;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class ClassUtils {

  public static String toString(Object o) {
    return toString(o, true);
  }

  public static String toString(Object o, boolean format) {
    ArrayList<String> list = new ArrayList<String>();
    ClassUtils.toString(o, o.getClass(), list);
    if (format) {
      StringBuffer sb = new StringBuffer();
      for (int i = 0; i < list.size(); i++) {
        if (i == list.size() - 1) {
          sb.append("  ").append(list.get(i));
        } else {
          sb.append("  ").append(list.get(i)).append("\n");
        }
      }
      return o.getClass().getName().concat("\n").concat(sb.toString());
    } else {
      return o.getClass().getName().concat(list.toString());
    }
  }

  private static void toString(Object o, Class<?> clazz, List<String> list) {
    Field f[] = clazz.getDeclaredFields();
    AccessibleObject.setAccessible(f, true);
    for (int i = 0; i < f.length; i++) {
      try {
        list.add(f[i].getName() + "=" + f[i].get(o));
      } catch (IllegalAccessException e) {
        e.printStackTrace();
      }
    }
    if (clazz.getSuperclass().getSuperclass() != null) {
      toString(o, clazz.getSuperclass(), list);
    }
  }
}
