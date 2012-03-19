package com.tscp.mvne.config;

import com.tscp.mvne.exception.InitializationException;

public class DOMAIN extends Config {
  public static String urlSupport;
  public static String urlManage;
  public static String urlTOS;

  public static final void init() throws InitializationException {
    Config.loadAll();
    try {
      urlSupport = props.getProperty("truconnect.url.support");
      urlManage = props.getProperty("truconnect.url.manage");
      urlTOS = props.getProperty("truconnect.url.tos");
    } catch (Exception e) {
      e.printStackTrace();
      throw new InitializationException(e);
    }

  }
}
