package com.tscp.mvne.config;

/**
 * Contains the values of connectionProperties from ConfigLocation.
 * 
 * @author Tachikoma
 * 
 */
public final class ConnectionConfig extends Config {
  public static boolean initialized = false;
  public static String billingNameSpace;
  public static String billingServiceName;
  public static String billingWSDL;
  public static String networkNameSpace;
  public static String networkServiceName;
  public static String networkWSDL;
  public static String emailServer;

  public ConnectionConfig() {
    init(PropertiesLocation.connectionFile);
    if (!initialized) {
      loadValues();
    }
  }

  public static final void init() {
    init(PropertiesLocation.connectionFile);
    loadValues();
  }

  private static final void loadValues() {
    // Billing Service values
    billingNameSpace = properties.getProperty("billing.namespace");
    billingServiceName = properties.getProperty("billing.servicename");
    billingWSDL = properties.getProperty("billing.location");
    // Network Service values
    networkNameSpace = properties.getProperty("network.namespace");
    networkServiceName = properties.getProperty("network.servicename");
    networkWSDL = properties.getProperty("network.location");
    // Email Service values
    emailServer = properties.getProperty("email.smtphost");
    // Set initialized
    initialized = true;
  }
}
