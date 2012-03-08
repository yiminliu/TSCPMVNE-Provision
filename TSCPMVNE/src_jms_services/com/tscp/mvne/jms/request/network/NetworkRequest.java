package com.tscp.mvne.jms.request.network;

import com.tscp.mvne.customer.dao.DeviceInfo;
import com.tscp.mvne.jms.request.Request;

public class NetworkRequest extends Request {
  private static final long serialVersionUID = -9122234144397151083L;
  private int customerId;
  private int accountNumber;
  private DeviceInfo deviceInfo = new DeviceInfo();
  private NetworkRequestType requestType;

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

  public NetworkRequestType getRequestType() {
    return requestType;
  }

  public void setRequestType(NetworkRequestType requestType) {
    this.requestType = requestType;
  }

  @Override
  public String toString() {
    return "NetworkRequest [customerId=" + customerId + ", accountNumber=" + accountNumber + ", deviceInfo=" + deviceInfo
        + ", requestType=" + requestType + ", attemptNumber=" + attemptNumber + ", history=" + history + "]";
  }

}
