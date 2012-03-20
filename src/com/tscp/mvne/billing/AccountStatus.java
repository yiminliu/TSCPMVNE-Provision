package com.tscp.mvne.billing;

import java.io.Serializable;

/**
 * This is a temporary class that is used to store a lightweight view of the
 * FULL state of an account. This includes the state in Kenan, the Network and
 * the Device.
 * 
 * @author Tachikoma
 * 
 */
public class AccountStatus implements Serializable {
  private static final long serialVersionUID = -8944931112627786270L;
  private String billingStatus;
  private String networkStatus;
  private String deviceStatus;

  public String getBillingStatus() {
    return billingStatus;
  }

  public void setBillingStatus(String billingStatus) {
    this.billingStatus = billingStatus;
  }

  public String getNetworkStatus() {
    return networkStatus;
  }

  public void setNetworkStatus(String networkStatus) {
    this.networkStatus = networkStatus;
  }

  public String getDeviceStatus() {
    return deviceStatus;
  }

  public void setDeviceStatus(String deviceStatus) {
    this.deviceStatus = deviceStatus;
  }

  @Override
  public String toString() {
    return "AccountStatus [billingStatus=" + billingStatus + ", networkStatus=" + networkStatus + ", deviceStatus=" + deviceStatus + "]";
  }

}
