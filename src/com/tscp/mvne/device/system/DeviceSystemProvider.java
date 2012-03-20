package com.tscp.mvne.device.system;


public class DeviceSystemProvider {
  private static final DeviceSystem instance = new DeviceSystem();

  protected DeviceSystemProvider() {
    // prevent instantiation
  }

  public static final DeviceSystem getInstance() {
    return instance;
  }

}
