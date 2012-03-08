package com.tscp.mvne.service.util;

import com.tscp.mvne.billing.system.BillingSystem;
import com.tscp.mvne.jms.request.account.ServiceInstanceRequest;
import com.tscp.mvne.network.NetworkInfo;
import com.tscp.mvne.network.NetworkSystem;
import com.tscp.mvne.system.provider.BillingSystemProvider;
import com.tscp.mvne.system.provider.NetworkSystemProvider;

public final class ServiceInstanceUtil {
  protected static final NetworkSystem networkSystem = NetworkSystemProvider.getInstance();
  protected static final BillingSystem billingSystem = BillingSystemProvider.getInstance();

  public static final void fetchNetworkInfo(ServiceInstanceRequest request) {
    if (request.getDeviceInfo().getNetworkInfo() == null) {
      NetworkInfo networkInfo = networkSystem.getNetworkInfo(request.getDeviceInfo().getDeviceValue(), null);
      request.getDeviceInfo().setNetworkInfo(networkInfo);
    }
  }

  public static final void fetchServiceInstance(ServiceInstanceRequest request) {
    if (request.getDeviceInfo().getServiceInstance() == null) {
      request.getDeviceInfo().setServiceInstance(billingSystem.getServiceInstance(request.getDeviceInfo()));
    }
  }
}
