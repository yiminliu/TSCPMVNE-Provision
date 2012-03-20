package com.tscp.mvne.util.audit;

import java.util.HashSet;
import java.util.Set;

public class Status {
  private int custId;
  private int accountNo;
  private KenanState kenanState = new KenanState();
  private NetworkState networkState = new NetworkState();
  private DeviceState deviceState = new DeviceState();
  private Set<String> errors = new HashSet<String>();

  public int getCustId() {
    return custId;
  }

  public void setCustId(int custId) {
    this.custId = custId;
  }

  public int getAccountNo() {
    return accountNo;
  }

  public void setAccountNo(int accountNo) {
    this.accountNo = accountNo;
  }

  public KenanState getKenanState() {
    return kenanState;
  }

  public void setKenanState(KenanState kenanState) {
    this.kenanState = kenanState;
  }

  public NetworkState getNetworkState() {
    return networkState;
  }

  public void setNetworkState(NetworkState networkState) {
    this.networkState = networkState;
  }

  public DeviceState getDeviceState() {
    return deviceState;
  }

  public void setDeviceState(DeviceState deviceState) {
    this.deviceState = deviceState;
  }

  public Set<String> getErrors() {
    return errors;
  }

  public void setErrors(Set<String> errors) {
    this.errors = errors;
  }

}
