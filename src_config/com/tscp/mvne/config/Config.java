package com.tscp.mvne.config;

import java.io.IOException;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tscp.mvne.exception.InitializationException;

public class Config {
  public static final String connectionFile = "com/tscp/mvne/config/connection.tscpmvne.properties";
  public static final String deviceFile = "com/tscp/mvne/config/device.properties";
  public static final String billingFile = "com/tscp/mvne/config/truConnectDefaults.properties";
  protected static final Logger logger = LoggerFactory.getLogger("TSCPMVNELogger");
  protected static Set<String> loadedFiles = new HashSet<String>();
  protected static Properties props = new Properties();

  public static void init() {
    Connection.init();
    Provision.init();
    Network.init();
  }

  protected static void loadAll() {
    load(connectionFile);
    load(deviceFile);
    load(billingFile);
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