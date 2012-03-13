package com.tscp.mvne.billing.provisioning.service;

import java.util.List;

import com.tscp.mvne.billing.Component;
import com.tscp.mvne.billing.Package;
import com.tscp.mvne.billing.ServiceInstance;
import com.tscp.mvne.billing.provisioning.ProvisionSystem;
import com.tscp.mvne.billing.provisioning.ProvisionSystemProvider;
import com.tscp.mvne.config.ProvisionConfig;

public class ProvisioningService {
  private static final ProvisionSystem system = ProvisionSystemProvider.getInstance();

  public Component getActiveComponent(int accountNumber, String externalId) {
    return system.getActiveComponent(accountNumber, externalId);
  }

  public List<Component> getActiveComponents(int accountNumber, String externalId) {
    return system.getActiveComponents(accountNumber, externalId);
  }

  public Package getActivePackage(int accountNumber) {
    return system.getActivePackage(accountNumber);
  }

  public List<Package> getActivePackages(int accountNumber) {
    return system.getActivePackages(accountNumber);
  }

  public ServiceInstance getActiveService(int accountNumber) {
    return system.getActiveService(accountNumber);
  }

  public List<ServiceInstance> getActiveServices(int accountNumber) {
    return system.getActiveServices(accountNumber);
  }

  public void removeActiveComponent(int accountNumber, String externalId) {
    ServiceInstance serviceInstance = getActiveService(accountNumber);
    Component component = getActiveComponent(accountNumber, serviceInstance.getExternalId());
    removeComponent(accountNumber, externalId, component.getInstanceId());
  }

  public void removeComponent(int accountNumber, String externalId, int componentInstanceId) {
    system.removeComponent(accountNumber, externalId, componentInstanceId);
  }

  public void addComponent(int accountNumber, String externalId, int componentId) {
    system.addComponent(accountNumber, externalId, componentId);
  }

  public void addInstallComopnent(int accountNumber, String externalId) {
    addComponent(accountNumber, externalId, Integer.parseInt(ProvisionConfig.Component.installId));
  }

  public void addReinstallComponent(int accountNumber, String externalId) {
    addComponent(accountNumber, externalId, Integer.parseInt(ProvisionConfig.Component.reinstallId));
  }

  public void addSuspendComponent(int accountNumber, String externalId) {
    addComponent(accountNumber, externalId, Integer.parseInt(ProvisionConfig.Component.suspendId));
  }

}