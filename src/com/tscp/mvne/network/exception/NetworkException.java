package com.tscp.mvne.network.exception;

import com.tscp.mvne.exception.MVNEException;
import com.tscp.mvne.network.NetworkInfo;

public class NetworkException extends MVNEException {
  private static final long serialVersionUID = -7412354082633059506L;
  private NetworkInfo networkinfo;

  public NetworkException() {
    super();
  }

  public NetworkException(String message, Exception e) {
    super(message, e);
  }

  public NetworkException(String methodName, String message, Exception e) {
    super(methodName, message, e);
  }

  public NetworkException(String methodName, String message) {
    super(methodName, message);
  }

  public NetworkException(String message) {
    super(message);
  }

  public NetworkInfo getNetworkinfo() {
    return networkinfo;
  }

  public void setNetworkinfo(NetworkInfo networkinfo) {
    this.networkinfo = networkinfo;
  }

}
