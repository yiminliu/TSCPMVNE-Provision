package com.tscp.mvne.system.provider;

import com.tscp.mvne.network.NetworkSystem;

public class NetworkSystemProvider {
  private static final NetworkSystem instance = new NetworkSystem();

  protected NetworkSystemProvider() {
    // prevent instantiation
  }
  
  public static final NetworkSystem getInstance() {
    return instance;
  }

}
