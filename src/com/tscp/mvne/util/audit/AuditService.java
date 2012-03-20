package com.tscp.mvne.util.audit;

import com.tscp.mvne.billing.exception.ProvisionException;
import com.tscp.mvne.billing.provisioning.Component;
import com.tscp.mvne.billing.provisioning.Package;
import com.tscp.mvne.billing.provisioning.ServiceInstance;
import com.tscp.mvne.billing.provisioning.service.ProvisionService;
import com.tscp.mvne.billing.service.BillService;
import com.tscp.mvne.customer.DeviceException;
import com.tscp.mvne.device.Device;
import com.tscp.mvne.device.service.DeviceService;
import com.tscp.mvne.network.NetworkInfo;
import com.tscp.mvne.network.exception.NetworkException;
import com.tscp.mvne.network.service.NetworkService;

public class AuditService {
  private static BillService billService = new BillService();
  private static ProvisionService provisionService = new ProvisionService();
  private static DeviceService deviceService = new DeviceService();
  private static NetworkService networkService = new NetworkService();

  public static Status getStatus(int custId, int accountNo) throws ProvisionException, NetworkException, DeviceException {
    Status status = new Status();
    status.setAccountNo(accountNo);
    status.setCustId(custId);
    getEmail(status, accountNo);
    getServiceInstance(status, accountNo);
    getComponent(status, accountNo, status.getKenanState().getServiceInstance().getExternalId());
    getPackage(status, accountNo);
    getDevice(status, custId, accountNo);
    getNetworkInfo(status, status.getDeviceState().getDevice().getValue(), status.getKenanState().getServiceInstance().getExternalId());
    return status;
  }

  private static void getEmail(Status status, int accountNo) {
    try {
      String email = billService.getEmail(accountNo);
      status.getKenanState().setEmail(email);
    } catch (Exception e) {
      status.getKenanState().setEmail(null);
      status.getErrors().add("No email address in Kenan");
    }
  }

  private static void getServiceInstance(Status status, int accountNo) {
    try {
      ServiceInstance serviceInstance = provisionService.getActiveService(accountNo);
      status.getKenanState().setServiceInstance(serviceInstance);
    } catch (ProvisionException e) {
      status.getKenanState().setServiceInstance(null);
      status.getErrors().add("No active service in Kenan");
    }
  }

  private static void getComponent(Status status, int accountNo, String externalId) {
    try {
      Component component = provisionService.getActiveComponent(accountNo, externalId);
      status.getKenanState().setComponent(component);
    } catch (ProvisionException e) {
      status.getKenanState().setComponent(null);
      status.getErrors().add("No active component in Kenan");
    }
  }

  private static void getPackage(Status status, int accountNo) {
    try {
      Package pkg = provisionService.getActivePackage(accountNo);
      status.getKenanState().setPkg(pkg);
    } catch (Exception e) {
      status.getKenanState().setPkg(null);
      status.getErrors().add("No package found in Kenan");
    }
  }

  private static void getDevice(Status status, int custId, int accountNo) {
    try {
      Device device = deviceService.getDevice(custId, accountNo);
      status.getDeviceState().setDevice(device);
    } catch (DeviceException e) {
      status.getDeviceState().setDevice(null);
      status.getErrors().add("No device found");
    }
  }

  private static void getNetworkInfo(Status status, String esn, String externalId) {
    try {
      NetworkInfo deviceNetworkInfo = networkService.getNetworkInfo(esn, null);
      status.getNetworkState().setDeviceNetworkInfo(deviceNetworkInfo);
    } catch (NetworkException e) {
      status.getNetworkState().setDeviceNetworkInfo(null);
      status.getErrors().add("No network info for device");
    }
    try {
      NetworkInfo accountNetworkInfo = networkService.getNetworkInfo(null, externalId);
      status.getNetworkState().setAccountNetworkInfo(accountNetworkInfo);
    } catch (NetworkException e) {
      status.getNetworkState().setAccountNetworkInfo(null);
      status.getErrors().add("No network info for account");
    }
  }

}
