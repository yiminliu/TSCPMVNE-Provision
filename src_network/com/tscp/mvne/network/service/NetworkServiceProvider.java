package com.tscp.mvne.network.service;

import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.namespace.QName;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tscp.mvne.config.Config;
import com.tscp.mvne.config.Connection;
import com.tscp.mvne.exception.InitializationException;
import com.tscp.mvno.webservices.API3;
import com.tscp.mvno.webservices.API3Service;

/**
 * Instantiates and provides access to a singleton of API3Service.
 * 
 * @author Tachikoma
 * 
 */
public final class NetworkServiceProvider {
  private static final Logger logger = LoggerFactory.getLogger("TSCPMVNELogger");
  private static final API3Service service = loadInterface();
  private static final API3 port = service.getAPI3Port();

  protected NetworkServiceProvider() {
    // prevent instantiation
  }

  /**
   * Loads and returns the API3 Service.
   * 
   * @return
   */
  protected static final API3Service loadInterface() throws InitializationException {
    Config.init();
    try {
      URL url = new URL(Connection.networkWSDL);
      QName qName = new QName(Connection.networkNameSpace, Connection.networkServiceName);
      logger.info("{} has been initialized WSDL:{}", Connection.networkServiceName, Connection.networkWSDL);
      return new API3Service(url, qName);
    } catch (MalformedURLException url_ex) {
      logger.error("{} failed to initialize WSDL:{}", Connection.networkServiceName, Connection.networkWSDL);
      throw new InitializationException(url_ex);
    }
  }

  /**
   * Returns the singleton instance of the API3 Service.
   * 
   * @return
   */
  public static final API3 getInstance() {
    return port;
  }
}
