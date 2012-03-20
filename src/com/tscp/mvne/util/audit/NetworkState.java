package com.tscp.mvne.util.audit;

import com.tscp.mvne.network.NetworkInfo;

public class NetworkState {
  private NetworkInfo deviceNetworkInfo;
  private NetworkInfo accountNetworkInfo;

  public NetworkState() {

  }

  public NetworkState(NetworkInfo deviceNetworkInfo, NetworkInfo accountNetworkInfo) {
    this.deviceNetworkInfo = deviceNetworkInfo;
    this.accountNetworkInfo = accountNetworkInfo;
  }

  public NetworkInfo getDeviceNetworkInfo() {
    return deviceNetworkInfo;
  }

  public void setDeviceNetworkInfo(NetworkInfo deviceNetworkInfo) {
    this.deviceNetworkInfo = deviceNetworkInfo;
  }

  public NetworkInfo getAccountNetworkInfo() {
    return accountNetworkInfo;
  }

  public void setAccountNetworkInfo(NetworkInfo accountNetworkInfo) {
    this.accountNetworkInfo = accountNetworkInfo;
  }

}
