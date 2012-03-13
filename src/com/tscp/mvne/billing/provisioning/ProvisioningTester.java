package com.tscp.mvne.billing.provisioning;

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
  public void addComponent(int accountNumber, String externalId, int componentId) {
    service.addComponent(accountNumber, externalId, componentId);
  }

  @WebMethod
  public void addInstallComponent(int accountNumber, String externalId) {
    service.addInstallComopnent(accountNumber, externalId);
  }

  @WebMethod
  public void addReinstallComponent(int accountNumber, String externalId) {
    service.addReinstallComponent(accountNumber, externalId);
  }

  @WebMethod
  public void addSuspendComponent(int accountNumber, String externalId) {
    service.addSuspendComponent(accountNumber, externalId);
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
  public void removeActiveComponent(int accountNumber, String externalId) {
    service.removeActiveComponent(accountNumber, externalId);
  }

  @WebMethod
  public void removeComponent(int accountNumber, String externalId, int componentInstanceId) {
    service.removeComponent(accountNumber, externalId, componentInstanceId);
  }

}
