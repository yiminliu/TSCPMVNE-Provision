package com.tscp.mvne.config;

import java.io.IOException;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tscp.mvne.exception.InitializationException;

public class Config {
  public static final String configFile = "com/tscp/mvne/config/config.properties";
  public static final String connectionFile = "com/tscp/mvne/config/service.properties";
  public static final String deviceFile = "com/tscp/mvne/config/device.properties";
  public static final String domainFile = "com/tscp/mvne/config/domain.properties";
  public static final String provisionFile = "com/tscp/mvne/config/provision.properties";
  protected static final Logger logger = LoggerFactory.getLogger("TSCPMVNELogger");
  protected static Set<String> loadedFiles = new HashSet<String>();
  protected static Properties props = new Properties();
  protected static boolean production = false;

  public static void init() throws InitializationException {
    load(configFile);
    if (props.getProperty("production") != null && props.getProperty("production").equals("1")) {
      production = true;
    } else {
      production = false;
    }
  }

  public static void initAll() throws InitializationException {
    Config.init();
    CONNECTION.init();
    DEVICE.init();
    DOMAIN.init();
    PROVISION.init();
  }

  protected static void loadAll() throws InitializationException {
    load(configFile);
    load(deviceFile);
    load(domainFile);
    load(provisionFile);
    load(connectionFile);
  }

  protected static void load(String filepath) throws InitializationException {
    if (!loadedFiles.contains(filepath)) {
      try {
        logger.info("LOADING PROPERTIES: {}", filepath);
        props.load(Config.class.getClassLoader().getResourceAsStream(filepath));
        loadedFiles.add(filepath);
      } catch (IOException e) {
        logger.error("UNABLE TO LOAD PROPERTIES FILE: {}", filepath);
        throw new InitializationException(e);
      }
    }
  }

}