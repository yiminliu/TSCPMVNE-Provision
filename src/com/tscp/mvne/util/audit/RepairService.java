package com.tscp.mvne.util.audit;

import javax.jws.WebMethod;

import com.tscp.mvne.billing.AccountStatus;
import com.tscp.mvne.billing.BillingUtil;
import com.tscp.mvne.billing.exception.BillingException;
import com.tscp.mvne.billing.exception.ProvisionException;
import com.tscp.mvne.billing.provisioning.Component;
import com.tscp.mvne.billing.provisioning.Package;
import com.tscp.mvne.billing.provisioning.ServiceInstance;
import com.tscp.mvne.billing.provisioning.service.ProvisionService;
import com.tscp.mvne.billing.service.BillService;
import com.tscp.mvne.config.PROVISION;
import com.tscp.mvne.customer.DeviceException;
import com.tscp.mvne.device.Device;
import com.tscp.mvne.device.DeviceStatus;
import com.tscp.mvne.device.service.DeviceService;
import com.tscp.mvne.network.NetworkInfo;
import com.tscp.mvne.network.exception.NetworkException;
import com.tscp.mvne.network.service.NetworkService;

public class RepairService {
  private static BillService billService = new BillService();
  private static ProvisionService provisionService = new ProvisionService();
  private static DeviceService deviceService = new DeviceService();
  private static NetworkService networkService = new NetworkService();

  /**
   * TODO this is a mockup function - accountStatus should contain all elements
   * ServiceInstance, Package, Component, NetworkInfo and DeviceInfo. It should
   * then audit the state of the account and repair it as needed
   * 
   * @param custId
   * @param accountNo
   * @param deviceId
   */
  private void restoreAndRepairAccount(int custId, int accountNo, int deviceId) {
    ServiceInstance serviceInstance = provisionService.getActiveService(accountNo);
    AccountStatus accountStatus = getAccountStatus(custId, accountNo, deviceId, serviceInstance.getExternalId());
    if (!accountStatus.getBillingStatus().equals("ACTIVE") || !accountStatus.getBillingStatus().equals("REINSTALL")) {
      Package pkg = provisionService.getActivePackage(accountNo);
      Component component = provisionService.getActiveComponent(accountNo, serviceInstance.getExternalId());
      boolean chargeMRC;
      try {
        chargeMRC = BillingUtil.checkChargeMRC(accountNo, serviceInstance.getExternalId());
      } catch (BillingException e) {
        chargeMRC = true;
      }
      int componentId = chargeMRC ? PROVISION.COMPONENT.INSTALL : PROVISION.COMPONENT.REINSTALL;
      provisionService.removeComponent(accountNo, serviceInstance.getExternalId(), pkg.getInstanceId(), component.getInstanceId());
      provisionService.addSingleComponent(accountNo, serviceInstance.getExternalId(), pkg.getInstanceId(), componentId);
    }
    if (!accountStatus.getDeviceStatus().equals("ACTIVE")) {
      Device device = deviceService.getDevice(custId, deviceId, accountNo);
      device.setStatusId(DeviceStatus.ID_ACTIVE);
      device.setStatus(DeviceStatus.DESC_ACTIVE);
      device.save();
    }
    if (!accountStatus.getNetworkStatus().equals("ACTIVE")) {
      NetworkInfo networkInfo = networkService.getNetworkInfo(null, serviceInstance.getExternalId());
      networkService.restoreService(networkInfo);
    }
  }

  /**
   * Temporary "lightweight" method to get the full state of the account.
   * 
   * @param custId
   * @param accountNo
   * @param deviceId
   * @return
   */
  private AccountStatus getAccountStatus(int custId, int accountNo, Device device, String externalId) throws DeviceException, NetworkException,
      ProvisionException {
    AccountStatus accountStatus = new AccountStatus();

    // get device status
    accountStatus.setDeviceStatus(device.getStatus().toUpperCase());

    // get network status
    NetworkInfo networkInfo = networkService.getNetworkInfo(device.getValue(), null);
    if (networkInfo != null && networkInfo.getStatus() != null) {
      if (networkInfo.getStatus().equals("A")) {
        accountStatus.setNetworkStatus("ACTIVE");
      } else if (networkInfo.getStatus().equals("C")) {
        accountStatus.setNetworkStatus("CANCEL");
      } else if (networkInfo.getStatus().equals("S")) {
        accountStatus.setNetworkStatus("SUSPEND");
      } else if (networkInfo.getStatus().equals("R")) {
        accountStatus.setNetworkStatus("RESERVE");
      }
    }

    // get billing status
    Component component = provisionService.getActiveComponent(accountNo, externalId);
    if (component.getId() == 500000) {
      accountStatus.setBillingStatus("ACTIVE");
    } else if (component.getId() == 500001) {
      accountStatus.setBillingStatus("REINSTALL");
    } else if (component.getId() == 500002) {
      accountStatus.setBillingStatus("SUSPEND");
    }

    return accountStatus;
  }

  /**
   * Temporary "lightweight" method to get the full state of the account.
   * 
   * @param custId
   * @param accountNo
   * @param deviceId
   * @return
   */
  @WebMethod
  public AccountStatus getAccountStatus(int custId, int accountNo, int deviceId) throws DeviceException, NetworkException, ProvisionException {
    ServiceInstance serviceInstance = provisionService.getActiveService(accountNo);
    return getAccountStatus(custId, accountNo, deviceId, serviceInstance.getExternalId());
  }

  /**
   * Temporary "lightweight" method to get the full state of the account.
   * 
   * @param custId
   * @param accountNo
   * @param deviceId
   * @return
   */
  private AccountStatus getAccountStatus(int custId, int accountNo, int deviceId, String externalId) throws DeviceException, NetworkException,
      ProvisionException {
    Device device = deviceService.getDevice(custId, deviceId, accountNo);
    return getAccountStatus(custId, accountNo, device, externalId);
  }
}
