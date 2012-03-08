package com.tscp.mvne.jms.request.account;

import com.tscp.mvne.customer.dao.DeviceInfo;
import com.tscp.mvne.jms.request.Request;

public class ServiceInstanceRequest extends Request {
  private static final long serialVersionUID = 2589522373409707062L;
  private int customerId;
  private int accountNumber;
  private DeviceInfo deviceInfo = new DeviceInfo();
  private ServiceInstanceRequestType requestType;

  public int getCustomerId() {
    return customerId;
  }

  public void setCustomerId(int customerId) {
    this.customerId = customerId;
  }

  public int getAccountNumber() {
    return accountNumber;
  }

  public void setAccountNumber(int accountNumber) {
    this.accountNumber = accountNumber;
  }

  public DeviceInfo getDeviceInfo() {
    return deviceInfo;
  }

  public void setDeviceInfo(DeviceInfo deviceInfo) {
    this.deviceInfo = deviceInfo;
  }

  public ServiceInstanceRequestType getRequestType() {
    return requestType;
  }

  public void setRequestType(ServiceInstanceRequestType requestType) {
    this.requestType = requestType;
  }

  @Override
  public String toString() {
    return "ServiceInstanceRequest [customerId=" + customerId + ", accountNumber=" + accountNumber + ", deviceInfo="
        + deviceInfo + ", requestType=" + requestType + ", attemptNumber=" + attemptNumber + ", history=" + history + "]";
  }

}
