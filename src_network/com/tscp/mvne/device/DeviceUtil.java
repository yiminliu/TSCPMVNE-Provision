package com.tscp.mvne.device;

import com.tscp.mvne.customer.DeviceException;

public class DeviceUtil {

  public static void checkDevice(Device device) throws DeviceException {
    if (device.getId() == 0) {
      throw new DeviceException("Device ID is not set");
    } else if (device.getAccountNo() == 0) {
      throw new DeviceException("Device account number is not set");
    }
  }

  public static void checkOwnership(int custId, Device device) throws DeviceException {
    if (device.getCustId() != custId) {
      throw new DeviceException("Cust " + custId + " does not own device " + device.getId() + " (belongs to cust " + device.getCustId() + ")");
    }
  }

}
