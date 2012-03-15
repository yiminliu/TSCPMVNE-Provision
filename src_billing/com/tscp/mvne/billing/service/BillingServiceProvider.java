package com.tscp.mvne.billing.service;

import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.namespace.QName;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tscp.mvne.billing.api.BillingServiceInterface;
import com.tscp.mvne.billing.api.BillingServiceInterfaceSoap;
import com.tscp.mvne.config.Config;
import com.tscp.mvne.config.Connection;
import com.tscp.mvne.exception.InitializationException;

/**
 * Instantiates and provides access to a singleton of
 * BillingServiceInterfaceSoap.
 * 
 * @author Tachikoma
 * 
 */
public final class BillingServiceProvider {
  private static final Logger logger = LoggerFactory.getLogger("TSCPMVNELogger");
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
  protected static final BillingServiceInterface loadInterface() throws InitializationException {
    Config.init();
    try {
      URL url = new URL(Connection.billingWSDL);
      QName qName = new QName(Connection.billingNameSpace, Connection.billingServiceName);
      logger.info("{} has been initialized WSDL:{}", Connection.billingServiceName, Connection.billingWSDL);
      return new BillingServiceInterface(url, qName);
    } catch (MalformedURLException url_ex) {
      logger.error("{} failed to initialize WSDL:{}", Connection.billingServiceName, Connection.billingWSDL);
      throw new InitializationException(url_ex);
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
