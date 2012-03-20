package com.tscp.mvne.billing.provisioning.service;

import java.util.List;

import com.tscp.mvne.billing.exception.ProvisionException;
import com.tscp.mvne.billing.provisioning.Component;
import com.tscp.mvne.billing.provisioning.Package;
import com.tscp.mvne.billing.provisioning.ProvisionSystem;
import com.tscp.mvne.billing.provisioning.ProvisionSystemProvider;
import com.tscp.mvne.billing.provisioning.ServiceInstance;
import com.tscp.mvne.config.PROVISION;

public class ProvisionService {
  private static final ProvisionSystem system = ProvisionSystemProvider.getInstance();

  protected void installService(int accountNo, String externalId, boolean reinstall) throws ProvisionException {
    addServiceInstance(accountNo, externalId);
    Package pkg = addPackage(accountNo, null);
    if (reinstall) {
      addComponent(accountNo, externalId, pkg.getInstanceId(), PROVISION.COMPONENT.REINSTALL);
    } else {
      addComponent(accountNo, externalId, pkg.getInstanceId(), PROVISION.COMPONENT.INSTALL);
    }
  }

  public void installService(int accountNo, String externalId) throws ProvisionException {
    installService(accountNo, externalId, false);
  }

  public void reinstallService(int accountNo, String externalId) throws ProvisionException {
    installService(accountNo, externalId, true);
  }

  public void disconnectService(int accountNo, String externalId) throws ProvisionException {
    system.removeServiceInstance(accountNo, externalId);
  }

  public void addComponent(int accountNumber, String externalId, int packageInstance, int componentId) throws ProvisionException {
    Component component = new Component(componentId);
    Package pkg;
    if (packageInstance == 0) {
      pkg = getActivePackage(accountNumber);
    } else {
      pkg = getActivePackage(accountNumber, packageInstance);
    }
    system.addComponent(accountNumber, externalId, pkg, component);
  }

  public Package addPackage(int accountNumber, Package pkg) throws ProvisionException {
    return system.addPackage(accountNumber, pkg);
  }

  public void addServiceInstance(int accountNumber, String externalId) throws ProvisionException {
    system.addServiceInstance(accountNumber, externalId);
  }

  public void addSingleComponent(int accountNumber, String externalId, int packageInstance, int componentId) throws ProvisionException {
    Component component = new Component(componentId);
    Package pkg;
    if (packageInstance == 0) {
      pkg = getActivePackage(accountNumber);
    } else {
      pkg = getActivePackage(accountNumber, packageInstance);
    }
    system.addSingleComponent(accountNumber, externalId, pkg, component);
  }

  public Component getActiveComponent(int accountNumber, String externalId) throws ProvisionException {
    List<Component> components = getActiveComponents(accountNumber, externalId);
    if (components == null || components.isEmpty()) {
      throw new ProvisionException("Account " + accountNumber + " returned no active component for MDN " + externalId);
    } else if (components.size() > 1) {
      throw new ProvisionException("Account " + accountNumber + " has more than one active component for MDN " + externalId);
    } else {
      return components.get(0);
    }
  }

  public List<Component> getActiveComponents(int accountNumber, String externalId) throws ProvisionException {
    return system.getActiveComponents(accountNumber, externalId);
  }

  public Package getActivePackage(int accountNumber) throws ProvisionException {
    List<Package> packages = getActivePackages(accountNumber);
    if (packages == null || packages.isEmpty()) {
      throw new ProvisionException("Account " + accountNumber + " has more than no active package");
    } else if (packages.size() > 1) {
      throw new ProvisionException("Account " + accountNumber + " has more than one active package");
    } else {
      return packages.get(0);
    }
  }

  public Package getActivePackage(int accountNumber, int packageInstance) throws ProvisionException {
    List<Package> packages = getActivePackages(accountNumber);
    if (packages != null) {
      for (Package pkg : packages) {
        if (pkg.getInstanceId() == packageInstance) {
          return pkg;
        }
      }
      throw new ProvisionException("Package " + packageInstance + " not found on account " + accountNumber);
    } else {
      throw new ProvisionException("No active packages found for account " + accountNumber);
    }
  }

  public List<Package> getActivePackages(int accountNumber) throws ProvisionException {
    return system.getActivePackages(accountNumber);
  }

  public ServiceInstance getActiveService(int accountNumber) throws ProvisionException {
    List<ServiceInstance> services = getActiveServices(accountNumber);
    if (services == null || services.isEmpty()) {
      throw new ProvisionException("Account " + accountNumber + " has no active service");
    } else if (services.size() > 1) {
      throw new ProvisionException("Account " + accountNumber + " has more than one active service");
    } else {
      return services.get(0);
    }
  }

  public List<ServiceInstance> getActiveServices(int accountNumber) throws ProvisionException {
    return system.getActiveServices(accountNumber);
  }

  public void removeComponent(int accountNumber, String externalId, int packageInstance, int componentInstance) throws ProvisionException {
    Package pkg;
    Component component;
    if (componentInstance == 0) {
      component = getActiveComponent(accountNumber, externalId);
    } else {
      component = new Component();
      component.setInstanceId(componentInstance);
    }
    if (packageInstance == 0) {
      pkg = getActivePackage(accountNumber);
    } else {
      pkg = getActivePackage(accountNumber, packageInstance);
    }
    system.removeComponent(accountNumber, externalId, pkg, component);
  }

  /**
   * This method is protected because the ability to remove packages is not
   * needed by the application.
   * 
   * @param accountNumber
   * @param packageInstance
   * @throws ProvisionException
   */
  protected void removePackage(int accountNumber, int packageInstance) throws ProvisionException {
    Package pkg;
    if (packageInstance == 0) {
      pkg = getActivePackage(accountNumber);
    } else {
      pkg = getActivePackage(accountNumber, packageInstance);
    }
    // system.removePackage(accountNumber, pkg);
  }

}