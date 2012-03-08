package com.tscp.mvne.service;

import java.util.Date;

import com.tscp.mvne.billing.Account;
import com.tscp.mvne.billing.Component;
import com.tscp.mvne.billing.ServiceInstance;
import com.tscp.mvne.billing.exception.BillingException;
import com.tscp.mvne.billing.system.BillingSystem;
import com.tscp.mvne.jms.request.account.ServiceInstanceRequest;
import com.tscp.mvne.jms.request.account.ServiceInstanceRequestType;
import com.tscp.mvne.network.exception.UsageDetailAccessException;
import com.tscp.mvne.service.exception.ServiceInstanceRequestException;
import com.tscp.mvne.service.util.ServiceInstanceUtil;
import com.tscp.mvne.service.util.UsageValidationUtil;
import com.tscp.mvne.system.provider.BillingSystemProvider;

public class ServiceInstanceService {
  protected static final BillingSystem billingSystem = BillingSystemProvider.getInstance();

  public void doServiceInstanceRequest(ServiceInstanceRequest request) throws ServiceInstanceRequestException {
    if (request.getDeviceInfo() != null) {
      ServiceInstanceUtil.fetchNetworkInfo(request);
      switch (request.getRequestType()) {
      case CREATE:
        createServiceInstance(request);
        break;
      case REINSTALL:
        createServiceInstance(request);
        break;
      case REMOVE:
        removeServiceInstance(request);
        break;
      case HOTLINE:
        hotlineServiceInstance(request);
        break;
      case RESTORE:
        restoreServiceInstance(request);
        break;
      default:
        break;
      }
    } else {
      String msg = "No DeviceInfo is attached to request for account {0,number,#}";
      throw new ServiceInstanceRequestException(request.getRequestType(), msg, request.getAccountNumber());
    }
  }

  protected void restoreServiceInstance(ServiceInstanceRequest req) throws ServiceInstanceRequestException {
    System.out.println("TSCPMVNE attempting to " + req.getRequestType() + " on MDN "
        + req.getDeviceInfo().getNetworkInfo().getMdn());
    try {
      ServiceInstanceUtil.fetchServiceInstance(req);
      billingSystem.updateServiceInstanceStatus(req.getDeviceInfo().getServiceInstance(),
        BillingSystem.SERVICE_INSTANCE_RESTORED);
    } catch (BillingException e) {
      final String msg = "Could not restore service instance from account {0,number,#}. Msg: {1}";
      throw new ServiceInstanceRequestException(ServiceInstanceRequestType.RESTORE, e.getCause(), msg, req.getAccountNumber(),
          e.getMessage());
    }
  }

  protected void hotlineServiceInstance(ServiceInstanceRequest req) throws ServiceInstanceRequestException {
    System.out.println("TSCPMVNE attempting to " + req.getRequestType() + " on MDN "
        + req.getDeviceInfo().getNetworkInfo().getMdn());
    try {
      billingSystem.updateServiceInstanceStatus(req.getDeviceInfo().getServiceInstance(),
        BillingSystem.SERVICE_INSTANCE_HOTLINED);
    } catch (BillingException e) {
      final String msg = "Could not hotline service instance from account {0,number,#}. Msg: {1}";
      throw new ServiceInstanceRequestException(ServiceInstanceRequestType.HOTLINE, e.getCause(), msg, req.getAccountNumber(),
          e.getMessage());
    }
  }

  protected void removeServiceInstance(ServiceInstanceRequest req) throws ServiceInstanceRequestException {
    System.out.println("TSCPMVNE attempting to " + req.getRequestType() + " on MDN "
        + req.getDeviceInfo().getNetworkInfo().getMdn());
    try {
      ServiceInstanceUtil.fetchServiceInstance(req);
      billingSystem.removeServiceInstance(req.getAccountNumber(), req.getDeviceInfo().getServiceInstance());
    } catch (BillingException e) {
      final String msg = "Could not remove service instance from account {0,number,#}. Msg: {1}";
      throw new ServiceInstanceRequestException(ServiceInstanceRequestType.REMOVE, e.getCause(), msg, req.getAccountNumber(), e
          .getMessage());
    }
  }

  protected void createServiceInstance(ServiceInstanceRequest req) throws ServiceInstanceRequestException {
    Date lastActiveDate;
    String mdn = req.getDeviceInfo().getNetworkInfo().getMdn();
    try {
      lastActiveDate = UsageValidationUtil.getLastActiveDate(req.getCustomerId(), req.getAccountNumber(), mdn);
      createServiceInstance(req, true);
//      if (UsageValidationUtil.requiresMRC(req.getCustomerId(), req.getAccountNumber(), mdn, lastActiveDate)) {
//        createServiceInstance(req, false);
//      } else {
//        createServiceInstance(req, true);
//      }
    } catch (UsageDetailAccessException e) {
      e.printStackTrace();
    }
  }

  private void createServiceInstance(ServiceInstanceRequest req, boolean reinstall) throws ServiceInstanceRequestException {
    System.out.println("TSCPMVNE attempting to " + req.getRequestType() + " on MDN "
        + req.getDeviceInfo().getNetworkInfo().getMdn() + " REINSTALL: " + reinstall);
    try {
      Account account = new Account(req.getAccountNumber());
      account.setServiceinstancelist(billingSystem.getServiceInstances(req.getAccountNumber()));
      ServiceInstance serviceInstance = new ServiceInstance(req.getDeviceInfo().getNetworkInfo());
      com.tscp.mvne.billing.Package lPackage = new com.tscp.mvne.billing.Package();
      Component component;
      if (reinstall) {
        component = new Component();
        component.setComponentId(BillingSystem.COMPONENT_REINSTALL);
      } else {
        component = null;
      }

      // TODO each of these billing requests needs to be rolled back should one
      // fail
      System.out.println("check 1");
      billingSystem.addServiceInstance(account, serviceInstance);
      System.out.println("check 2");
      billingSystem.addPackage(req.getAccountNumber(), serviceInstance, lPackage);
      System.out.println("check 3");
      billingSystem.addComponent(req.getAccountNumber(), serviceInstance, lPackage, component);
      System.out.println("check 4");
    } catch (BillingException e) {
      e.printStackTrace();
      final String msg = "Could not create service instance for account {0,number,#}. MSG: {1}";
      throw new ServiceInstanceRequestException(ServiceInstanceRequestType.CREATE, e.getCause(), msg, req.getAccountNumber(), e
          .getMessage());
    }
  }

}
