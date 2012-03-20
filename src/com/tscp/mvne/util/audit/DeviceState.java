package com.tscp.mvne.util.audit;

import com.tscp.mvne.device.Device;

public class DeviceState {
  private Device device;

  public DeviceState() {

  }

  public DeviceState(Device device) {
    this.device = device;
  }

  public Device getDevice() {
    return device;
  }

  public void setDevice(Device device) {
    this.device = device;
  }
}
