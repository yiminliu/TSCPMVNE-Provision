package com.tscp.mvne.service.util;

import com.tscp.mvne.customer.dao.DeviceInfo;
import com.tscp.mvne.device.DeviceException;
import com.tscp.mvne.network.NetworkInfo;
import com.tscp.mvne.network.NetworkInterface;

public final class NetworkServiceUtil {
  /**
   * Sets the appropriate ESN feild of the NetworkInfo object with the given
   * DeviceInfo value.
   * 
   * @param deviceInfo
   * @param networkInfo
   * @throws NetworkActivationException
   */
  public static void bindNetworkInfoToDeviceInfo(DeviceInfo deviceInfo, NetworkInfo networkInfo) throws DeviceException {
    switch (deviceInfo.getDeviceValue().length()) {
    case NetworkInterface.ESN_DEC_LENGTH:
      // TODO
    case NetworkInterface.MEID_DEC_LENGTH:
      networkInfo.setEsnmeiddec(deviceInfo.getDeviceValue());
      break;
    case NetworkInterface.ESN_HEX_LENGTH:
      // TODO
    case NetworkInterface.MEID_HEX_LENGTH:
      networkInfo.setEsnmeidhex(deviceInfo.getDeviceValue());
      break;
    default:
      throw new DeviceException("ESN " + deviceInfo.getDeviceValue() + " has invalid length");
    }
  }
}
