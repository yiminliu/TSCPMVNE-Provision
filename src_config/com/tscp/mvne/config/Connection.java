package com.tscp.mvne.config;

import com.tscp.mvne.exception.InitializationException;

/**
 * Contains the values of connectionProperties from ConfigLocation.
 * 
 * @author Tachikoma
 * 
 */
public final class Connection extends Config {
  public static String billingNameSpace;
  public static String billingServiceName;
  public static String billingWSDL;
  public static String networkNameSpace;
  public static String networkServiceName;
  public static String networkWSDL = "SOMTHINASDFLKSDJFS";
  public static String emailServer;

  public static final void init() throws InitializationException {
    Config.loadAll();
    try {
      billingNameSpace = props.getProperty("billing.namespace");
      billingServiceName = props.getProperty("billing.servicename");
      billingWSDL = props.getProperty("billing.location");
      networkNameSpace = props.getProperty("network.namespace");
      networkServiceName = props.getProperty("network.servicename");
      networkWSDL = props.getProperty("network.location");
      emailServer = props.getProperty("email.smtphost");
    } catch (Exception e) {
      e.printStackTrace();
      throw new InitializationException(e);
    }
  }

}