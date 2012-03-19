package com.tscp.mvne.device;


public class DeviceSystemProvider {
  private static final DeviceSystem instance = new DeviceSystem();

  protected DeviceSystemProvider() {
    // prevent instantiation
  }

  public static final DeviceSystem getInstance() {
    return instance;
  }

}
