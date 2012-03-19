package com.tscp.mvne.config;

import com.tscp.mvne.exception.InitializationException;

/**
 * Contains the values of connectionProperties from ConfigLocation.
 * 
 * @author Tachikoma
 * 
 */
public final class CONNECTION extends Config {
  public static String billingNameSpace;
  public static String billingServiceName;
  public static String billingWSDL;
  public static String networkNameSpace;
  public static String networkServiceName;
  public static String networkWSDL;
  public static String emailServer;

  public static final void init() throws InitializationException {
    Config.loadAll();
    String billingPrefix = Config.production ? "billing.prod." : "billing.dev.";
    String networkPrefix = Config.production ? "network.prod." : "network.dev.";
    try {
      billingNameSpace = props.getProperty(billingPrefix + "service.namespace");
      billingServiceName = props.getProperty(billingPrefix + "service.servicename");
      billingWSDL = props.getProperty(billingPrefix + "service.wsdl");
      networkNameSpace = props.getProperty(networkPrefix + "service.namespace");
      networkServiceName = props.getProperty(networkPrefix + "service.servicename");
      networkWSDL = props.getProperty(networkPrefix + "service.wsdl");
      emailServer = props.getProperty("email.telscape.smtphost");
    } catch (Exception e) {
      e.printStackTrace();
      throw new InitializationException(e);
    }
  }

}