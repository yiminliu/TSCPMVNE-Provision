package com.tscp.mvne.service.util;

import java.text.SimpleDateFormat;

import com.tscp.mvne.billing.Account;
import com.tscp.mvne.billing.ServiceInstance;
import com.tscp.mvne.customer.dao.DeviceInfo;
import com.tscp.mvne.network.NetworkInfo;
import com.tscp.mvne.network.NetworkInterface;
import com.tscp.mvne.network.exception.ESNMismatchException;
import com.tscp.mvne.network.exception.MDNMismatchException;
import com.tscp.mvne.network.exception.NetworkStatusException;

public final class NetworkValidationUtil {

  public static boolean checkDeviceOwnership(DeviceInfo deviceInfo, NetworkInfo networkInfo, ServiceInstance serviceInstance)
      throws ESNMismatchException, MDNMismatchException {
    return checkEsn(deviceInfo, networkInfo) && checkMdn(serviceInstance, networkInfo);
  }

  public static boolean checkSuspend(NetworkInfo networkInfo) throws NetworkStatusException {
    if (networkInfo != null && networkInfo.getStatus() != null) {
      if (networkInfo.getStatus().equals(NetworkInterface.DEVICE_STATUS_SUSPENDED)) {
        throw new NetworkStatusException("MDN {0} is already suspended", networkInfo.getMdn());
      } else {
        return true;
      }
    } else {
      throw new NetworkStatusException("NetworkInfo for Device with ESN {0} is empty", networkInfo.getEsnmeiddec());
    }
  }

  public static boolean checkRestore(NetworkInfo networkInfo) throws NetworkStatusException {
    if (networkInfo != null && networkInfo.getStatus() != null) {
      if (networkInfo.getStatus().equals(NetworkInterface.DEVICE_STATUS_ACTIVE)) {
        throw new NetworkStatusException("MDN {0} is already active", networkInfo.getMdn());
      } else {
        return true;
      }
    } else {
      throw new NetworkStatusException("NetworkInfo for Device with ESN {0} is empty", networkInfo.getEsnmeiddec());
    }
  }

  public static boolean checkActive(NetworkInfo networkInfo) throws NetworkStatusException {
    if (networkInfo != null && networkInfo.getStatus() != null) {
      if (!networkInfo.getStatus().equals(NetworkInterface.DEVICE_STATUS_ACTIVE)
          && !networkInfo.getStatus().equals(NetworkInterface.DEVICE_STATUS_SUSPENDED)) {
        return true;
      } else {
        throw new NetworkStatusException("Device with ESN {0} is already active on MDN {1}", networkInfo.getEsnmeiddec(),
            networkInfo.getMdn());
      }
    } else {
      return true;
    }
  }

  public static boolean checkAccount(Account account) throws NetworkStatusException {
    if (account != null && account.getInactive_date() != null) {
      SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
      throw new NetworkStatusException("The account for this device was closed on {0,date}", sdf.format(account
          .getInactive_date()));
    } else {
      return true;
    }
  }

  public static boolean checkEsn(DeviceInfo deviceInfo, NetworkInfo networkInfo) throws ESNMismatchException {
    if (!deviceInfo.getDeviceValue().equals(networkInfo.getEsnmeiddec())
        && !deviceInfo.getDeviceValue().equals(networkInfo.getEsnmeidhex())) {
      throw new ESNMismatchException("Device ID {0,number,#} does not match ESN returned by NetworkInfo query", deviceInfo
          .getDeviceId());
    } else {
      return true;
    }
  }

  public static boolean checkMdn(ServiceInstance serviceInstance, NetworkInfo networkInfo) throws MDNMismatchException {
    if (!serviceInstance.getExternalId().equals(networkInfo.getMdn())) {
      throw new MDNMismatchException(
          "ServiceInstance {0} for subscriber {1,number,#} does not match MDN returned by NetworkInfo query", serviceInstance
              .getExternalId(), serviceInstance.getSubscriberNumber());
    } else {
      return true;
    }
  }
}
