package com.tscp.mvne.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

public abstract class Config {
  protected static Set<String> loadedFiles = new HashSet<String>();
  protected static final Properties properties = new Properties();

  protected static final void init(String filepath) {
    if (!loadedFiles.contains(filepath)) {
      loadProperties(filepath);
    }
  }

  protected static final void loadProperties(String filepath) {
    System.out.println("CONFIG LOADING " + filepath);
    try {
      ClassLoader cl = Config.class.getClassLoader();
      InputStream in = cl.getResourceAsStream(filepath);
      properties.load(in);
      loadedFiles.add(filepath);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

}
