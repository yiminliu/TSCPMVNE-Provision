package com.tscp.mvne.billing.service;

import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.namespace.QName;

import com.tscp.mvne.billing.api.BillingServiceInterface;
import com.tscp.mvne.billing.api.BillingServiceInterfaceSoap;
import com.tscp.mvne.config.ConnectionConfig;
import com.tscp.mvne.config.ProvisionConfig;

/**
 * Instantiates and provides access to a singleton of
 * BillingServiceInterfaceSoap.
 * 
 * @author Tachikoma
 * 
 */
public final class BillingServiceProvider {
  private static final BillingServiceInterface serviceInterface = loadInterface();
  private static final BillingServiceInterfaceSoap port = serviceInterface.getBillingServiceInterfaceSoap();

  protected BillingServiceProvider() {
    // prevent instantiation
  }

  /**
   * Loads and returns the billing interface.
   * 
   * @return
   */
  protected static final BillingServiceInterface loadInterface() {
    System.out.println("BUILDING BILLING SERVICE");
    if (!ConnectionConfig.initialized) {
      ConnectionConfig.init();
    }
    if (!ProvisionConfig.initialized) {
      ProvisionConfig.init();
    }
    try {
      URL url = new URL(ConnectionConfig.billingWSDL);
      QName qName = new QName(ConnectionConfig.billingNameSpace, ConnectionConfig.billingServiceName);
      System.out.println("BUILT BILLING SYSTEM PROPERLY: " + ConnectionConfig.billingWSDL);
      return new BillingServiceInterface(url, qName);
    } catch (MalformedURLException url_ex) {
      System.out.println("EXC BUILDING EMPTY BILLING SERVICE");
      return new BillingServiceInterface();
    }
  }

  /**
   * Returns the singleton instance of the billing interface.
   * 
   * @return
   */
  public static final BillingServiceInterfaceSoap getInstance() {
    return port;
  }
}
