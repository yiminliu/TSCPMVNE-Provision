package com.tscp.mvne.billing.service;

import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.namespace.QName;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.telscape.billingserviceinterface.BillingServiceInterface;
import com.telscape.billingserviceinterface.BillingServiceInterfaceSoap;
import com.tscp.mvne.config.Config;
import com.tscp.mvne.config.CONNECTION;
import com.tscp.mvne.exception.InitializationException;

/**
 * Instantiates and provides access to a singleton of
 * BillingServiceInterfaceSoap.
 * 
 * @author Tachikoma
 * 
 */
public final class BillingGatewayProvider {
  private static final Logger logger = LoggerFactory.getLogger("TSCPMVNELogger");
  private static final BillingServiceInterface serviceInterface = loadInterface();
  private static final BillingServiceInterfaceSoap port = serviceInterface.getBillingServiceInterfaceSoap();

  protected BillingGatewayProvider() {
    // prevent instantiation
  }

  /**
   * Loads and returns the billing interface.
   * 
   * @return
   */
  protected static final BillingServiceInterface loadInterface() throws InitializationException {
    Config.initAll();
    try {
      URL url = new URL(CONNECTION.billingWSDL);
      QName qName = new QName(CONNECTION.billingNameSpace, CONNECTION.billingServiceName);
      logger.info("{} has been initialized WSDL:{}", CONNECTION.billingServiceName, CONNECTION.billingWSDL);
      return new BillingServiceInterface(url, qName);
    } catch (MalformedURLException url_ex) {
      logger.error("{} failed to initialize WSDL:{}", CONNECTION.billingServiceName, CONNECTION.billingWSDL);
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
