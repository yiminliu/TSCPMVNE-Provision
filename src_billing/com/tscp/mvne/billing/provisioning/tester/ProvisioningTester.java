package com.tscp.mvne.billing.provisioning.tester;

import java.util.Date;
import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebService;

import com.tscp.mvne.billing.Component;
import com.tscp.mvne.billing.Package;
import com.tscp.mvne.billing.ServiceInstance;
import com.tscp.mvne.billing.provisioning.service.ProvisioningService;

@WebService
public class ProvisioningTester {
  private ProvisioningService service = new ProvisioningService();

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
  
  @WebMethod
  public void removePackage(int accountNumber, int packageInstance) {
    service.removePackage(accountNumber, packageInstance);
  }
}
