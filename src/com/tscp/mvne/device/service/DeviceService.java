package com.tscp.mvne.device.service;

import java.util.List;

import com.tscp.mvne.customer.DeviceException;
import com.tscp.mvne.device.Device;
import com.tscp.mvne.device.system.DeviceSystem;
import com.tscp.mvne.device.system.DeviceSystemProvider;

public class DeviceService {
  private static final DeviceSystem system = DeviceSystemProvider.getInstance();

  public List<Device> getDevices(int custId, int accountNo) throws DeviceException {
    List<Device> devices = system.getDevices(custId, 0, accountNo);
    if (devices != null && !devices.isEmpty()) {
      return devices;
    } else {
      throw new DeviceException("Could not fetch devices for user " + custId + " account " + accountNo);
    }
  }

  public Device getDevice(int custId, int deviceId, int accountNo) throws DeviceException {
    List<Device> devices = system.getDevices(custId, deviceId, accountNo);
    if (devices == null || devices.isEmpty()) {
      throw new DeviceException("Could not fetch device " + deviceId + " for user " + custId + " account " + accountNo);
    } else if (devices.size() > 1) {
      throw new DeviceException("Found more than one device with ID " + deviceId + " for user " + custId + " account " + accountNo);
    } else {
      return devices.get(0);
    }
  }

  public Device getDevice(int custId, int accountNo) throws DeviceException {
    List<Device> devices = system.getDevices(custId, 0, accountNo);
    if (devices == null || devices.isEmpty()) {
      throw new DeviceException("Could not fetch device for user " + custId + " account " + accountNo);
    } else if (devices.size() > 1) {
      throw new DeviceException("Found more than one device  for user " + custId + " account " + accountNo);
    } else {
      return devices.get(0);
    }
  }

}
