package com.tscp.mvne.billing.provisioning.tester;

import java.util.Date;
import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebService;

import com.telscape.billingserviceinterface.BillName;
import com.telscape.billingserviceinterface.BillingAccount;
import com.telscape.billingserviceinterface.BillingService;
import com.telscape.billingserviceinterface.CustAddress;
import com.telscape.billingserviceinterface.PkgComponent;
import com.tscp.mvne.billing.BillingUtil;
import com.tscp.mvne.billing.provisioning.Component;
import com.tscp.mvne.billing.provisioning.Package;
import com.tscp.mvne.billing.provisioning.ProvisionUtil;
import com.tscp.mvne.billing.provisioning.ServiceInstance;
import com.tscp.mvne.billing.provisioning.service.ProvisionService;
import com.tscp.mvne.billing.service.BillService;

@WebService
public class ProvisioningTester {
  private ProvisionService service = new ProvisionService();
  private BillService billingService = new BillService();

  @WebMethod
  public void addComponent(int accountNumber, String externalId, int packageInstance, int componentId) {
    service.addComponent(accountNumber, externalId, packageInstance, componentId);
  }

  @WebMethod
  public void addPackage(int accountNumber, int packageId) {
    if (packageId == 0) {
      service.addPackage(accountNumber, null);
    } else {
      Package pkg = new Package();
      pkg.setAccountNumber(accountNumber);
      pkg.setId(packageId);
      pkg.setActiveDate(new Date());
      service.addPackage(accountNumber, pkg);
    }
  }

  @WebMethod
  public void addServiceInstance(int accountNumber, String externalId) {
    service.addServiceInstance(accountNumber, externalId);
  }

  @WebMethod
  public void addSingleComponent(int accountNumber, String externalId, int packageInstance, int componentId) {
    service.addSingleComponent(accountNumber, externalId, packageInstance, componentId);
  }

  @WebMethod
  public Component getActiveComponent(int accountNumber, String externalId) {
    return service.getActiveComponent(accountNumber, externalId);
  }

  @WebMethod
  public List<Component> getActiveComponents(int accountNumber, String externalId) {
    return service.getActiveComponents(accountNumber, externalId);
  }

  @WebMethod
  public Package getActivePackage(int accountNumber) {
    return service.getActivePackage(accountNumber);
  }

  @WebMethod
  public List<Package> getActivePackages(int accountNumber) {
    return service.getActivePackages(accountNumber);
  }

  @WebMethod
  public ServiceInstance getActiveService(int accountNumber) {
    return service.getActiveService(accountNumber);
  }

  @WebMethod
  public List<ServiceInstance> getActiveServices(int accountNumber) {
    return service.getActiveServices(accountNumber);
  }

  @WebMethod
  public void removeComponent(int accountNumber, String externalId, int componentInstanceId, int packageInstance) {
    service.removeComponent(accountNumber, externalId, componentInstanceId, packageInstance);
  }

}