package com.tscp.mvne.billing.provisioning;

import java.util.List;
import java.util.Vector;

import com.tscp.mvne.billing.Account;
import com.tscp.mvne.billing.BillingSystem;
import com.tscp.mvne.billing.Component;
import com.tscp.mvne.billing.Package;
import com.tscp.mvne.billing.ServiceInstance;
import com.tscp.mvne.billing.api.ArrayOfComponentHolder;
import com.tscp.mvne.billing.api.ArrayOfMessageHolder;
import com.tscp.mvne.billing.api.ArrayOfPackage;
import com.tscp.mvne.billing.api.ArrayOfPackageHolder;
import com.tscp.mvne.billing.api.ArrayOfPkgComponent;
import com.tscp.mvne.billing.api.ArrayOfServiceHolder;
import com.tscp.mvne.billing.api.ArrayOfValueHolder;
import com.tscp.mvne.billing.api.BillingService;
import com.tscp.mvne.billing.api.BillingServiceInterfaceSoap;
import com.tscp.mvne.billing.api.ComponentHolder;
import com.tscp.mvne.billing.api.MessageHolder;
import com.tscp.mvne.billing.api.PackageHolder;
import com.tscp.mvne.billing.api.PkgComponent;
import com.tscp.mvne.billing.api.ServiceHolder;
import com.tscp.mvne.billing.api.ValueHolder;
import com.tscp.mvne.billing.exception.ProvisionException;
import com.tscp.mvne.billing.service.BillingServiceProvider;
import com.tscp.mvne.exception.NoResponseException;

/**
 * Business rules currently only allow for one service per account to track
 * usage. Therefore there should only be one active service per account. If this
 * changes then much more complicated logic will have to be implemented across
 * the application.
 * 
 */
public class ProvisionSystem {
  private static final String USERNAME = "TSCPMVNE.ProvisioningSystem";
  protected static final short discReason = 5;
  protected static final BillingServiceInterfaceSoap service = BillingServiceProvider.getInstance();

  protected ProvisionSystem() {
    // prevent instantiation
  }

  public void addComponent(int accountNumber, String externalId, int componentId) throws ProvisionException {
    Component component = new Component();
    component.setId(componentId);
    addComponent(accountNumber, externalId, getActivePackage(accountNumber), component);
  }

  public void addComponent(int accountNumber, String externalId, Package pkg, Component component) throws ProvisionException {
    ProvisionUtil.checkAccountNumber(accountNumber);
    ProvisionUtil.checkExternalId(externalId);
    ProvisionUtil.checkPackage(pkg);
    try {
      ArrayOfPkgComponent componentList = ProvisionUtil.buildComponentList(externalId, pkg, component);
      ArrayOfMessageHolder messageHolder = service.addComponent(USERNAME, componentList);
      String message = ProvisionUtil.getResponse(messageHolder);
      if (!message.equals(ProvisionUtil.SUCCESS)) {
        throw new ProvisionException("Error adding component to account " + accountNumber + ". " + message);
      }
    } catch (Exception e) {
      throw new ProvisionException("Error adding component to account " + accountNumber, e);
    }
  }

  public Package addPackage(int accountNumber) throws ProvisionException {
    return addPackage(accountNumber, null);
  }

  public Package addPackage(int accountNumber, Package pkg) throws ProvisionException {
    ProvisionUtil.checkAccountNumber(accountNumber);
    ArrayOfPackage packages = ProvisionUtil.buildPackageList(pkg);
    ArrayOfValueHolder valueHolder = service.addPackage(USERNAME, packages);
    if (valueHolder != null && valueHolder.getValueHolder() != null && !valueHolder.getValueHolder().isEmpty()) {
      for (ValueHolder value : valueHolder.getValueHolder()) {
        if (value.getStatusMessage().getStatus().trim().equals("Success")) {
          pkg.setInstanceId(Integer.parseInt(value.getValue().trim()));
          pkg.setInstanceIdServ(value.getValue2().trim());
          return pkg;
        }
      }
      throw new ProvisionException("Error adding package " + pkg.getId() + " to Account " + accountNumber);
    } else {
      throw new NoResponseException("No response returned from Billing Server");
    }
  }

  /**
   * Adds a new service instance to the given account. If the external ID
   * already exists on the account the call to service.addService will fail from
   * a SQL constraint.
   * 
   * Error adding ServiceInstance ########## to account ######.
   * Error::ORA-20001: 316012, TRIG: INSERT/UPDATE Failed: Overlapping
   * active/inactive dates for external_id_type marked unique in
   * EXTERNAL_ID_TYPE_REF ORA-06512: at "ARBOR.EXTERNAL_ID_EQUIP_MAP_ATRIG"
   * 
   * @param accountNumber
   * @param externalId
   * @throws ProvisionException
   */
  public void addServiceInstance(int accountNumber, String externalId) throws ProvisionException {
    ProvisionUtil.checkAccountNumber(accountNumber);
    ProvisionUtil.checkExternalId(externalId);

    Account account = BillingSystem.getAccount(accountNumber);
    BillingService billingService = ProvisionUtil.getDefaultBillingService();
    billingService.setAccountNo(Integer.toString(account.getAccountno()));
    billingService.getServiceName().setFirstName(account.getFirstname());
    billingService.getServiceName().setMiddleName(account.getMiddlename());
    billingService.getServiceName().setLastName(account.getLastname());
    billingService.getServiceAddr().setAddress1(account.getContact_address1());
    billingService.getServiceAddr().setAddress2(account.getContact_address2());
    billingService.getServiceAddr().setCity(account.getContact_city());
    billingService.getServiceAddr().setState(account.getContact_state());
    billingService.getServiceAddr().setZip(account.getContact_zip());
    billingService.setExternalId(externalId);

    MessageHolder message = service.addService(USERNAME, billingService);
    if (message != null && message.getStatus() != null) {
      if (!message.getStatus().equals("Success")) {
        throw new ProvisionException("Error adding ServiceInstance " + externalId + " to account " + accountNumber + ". "
            + message.getMessage());
      }
    } else {
      throw new NoResponseException("addServiceInstance", "No response returned from foreign billing system.");
    }
  }

  public Component getActiveComponent(int accountNumber, String externalId) throws ProvisionException {
    List<Component> components = getActiveComponents(accountNumber, externalId);
    if (components.size() == 1) {
      return components.get(0);
    } else {
      throw new ProvisionException("More than one component is active on account " + accountNumber + " externalId "
          + externalId);
    }
  }

  public List<Component> getActiveComponents(int accountNumber, String externalId) throws ProvisionException {
    try {
      List<Component> results = new Vector<Component>();
      ArrayOfComponentHolder components = service.getListActiveComponent(USERNAME, Integer.toString(accountNumber), externalId);
      if (components != null && components.getComponentHolder() != null && !components.getComponentHolder().isEmpty()) {
        for (ComponentHolder componentHolder : components.getComponentHolder()) {
          results.add(ProvisionUtil.buildComponent(componentHolder.getComponent()));
        }
      }
      return results;
    } catch (Exception e) {
      throw new ProvisionException("Unable to get components from Billing Server for account " + accountNumber + " externalId "
          + externalId);
    }
  }

  public Package getActivePackage(int accountNumber) throws ProvisionException {
    List<Package> packages = getActivePackages(accountNumber);
    if (packages.size() == 1) {
      return packages.get(0);
    } else {
      throw new ProvisionException("More than one package is active on account " + accountNumber);
    }
  }

  public List<Package> getActivePackages(int accountNumber) throws ProvisionException {
    try {
      List<Package> results = new Vector<Package>();
      ArrayOfPackageHolder packages = service.getListActivePackages(USERNAME, Integer.toString(accountNumber));
      if (packages != null && packages.getPackageHolder() != null && !packages.getPackageHolder().isEmpty()) {
        for (PackageHolder packageHolder : packages.getPackageHolder()) {
          results.add(ProvisionUtil.buildPackage(packageHolder.getPackage()));
        }
      }
      return results;
    } catch (Exception e) {
      throw new ProvisionException("Unable to get packges from Billing Server for account " + accountNumber, e);
    }
  }

  public ServiceInstance getActiveService(int accountNumber) throws ProvisionException {
    List<ServiceInstance> services = getActiveServices(accountNumber);
    if (services.size() == 1) {
      return services.get(0);
    } else {
      throw new ProvisionException("More than one service is active on account " + accountNumber);
    }
  }

  public List<ServiceInstance> getActiveServices(int accountNumber) throws ProvisionException {
    try {
      List<ServiceInstance> results = new Vector<ServiceInstance>();
      ArrayOfServiceHolder services = service.getActiveService(USERNAME, Integer.toString(accountNumber));
      if (services != null && services.getServiceHolder() != null && !services.getServiceHolder().isEmpty()) {
        for (ServiceHolder serviceHolder : services.getServiceHolder()) {
          results.add(ProvisionUtil.buildServiceInstance(serviceHolder.getService()));
        }
      }
      return results;
    } catch (Exception e) {
      throw new ProvisionException("Unable to get services from Billing Server for account " + accountNumber, e);
    }
  }

  public void removeComponent(int accountNumber, String externalId, int componentInstanceId) throws ProvisionException {
    Component component = new Component();
    component.setInstanceId(componentInstanceId);
    removeComponent(accountNumber, externalId, getActivePackage(accountNumber), component);
  }

  public void removeComponent(int accountNumber, String externalId, Package pkg, Component component) throws ProvisionException {
    ProvisionUtil.checkAccountNumber(accountNumber);
    ProvisionUtil.checkExternalId(externalId);
    ProvisionUtil.checkPackage(pkg);
    ArrayOfPkgComponent componentList = ProvisionUtil.buildComponentList(externalId, pkg, component);
    for (PkgComponent pkgComponent : componentList.getPkgComponent()) {
      pkgComponent.setDiscReason(discReason);
      pkgComponent.setDiscDate(ProvisionUtil.getCalendar());
    }
    ArrayOfMessageHolder messageHolder = service.disconnectComponent(USERNAME, componentList);
    String message = ProvisionUtil.getResponse(messageHolder);
    if (!message.equals(ProvisionUtil.SUCCESS)) {
      throw new ProvisionException("Error removing component from Account " + accountNumber + ". " + message);
    }
  }

  public void removePackage(int accountNumber) throws ProvisionException {
    removePackage(accountNumber, getActivePackage(accountNumber));
  }

  public void removePackage(int accountNumber, Package pkg) throws ProvisionException {
    ProvisionUtil.checkAccountNumber(accountNumber);
    ProvisionUtil.checkPackage(pkg);
    com.tscp.mvne.billing.api.Package billingPackage = ProvisionUtil.buildBillingPackage(pkg);
    billingPackage.setDiscReason(discReason);
    billingPackage.setDiscDate(ProvisionUtil.getCalendar());
    ArrayOfPackage packages = new ArrayOfPackage();
    packages.getPackage().add(billingPackage);
    service.disconnectPackage(USERNAME, packages);
  }

  public void removeServiceInstance(int accountNumber, String externalId) throws ProvisionException {
    ProvisionUtil.checkAccountNumber(accountNumber);
    ProvisionUtil.checkExternalId(externalId);
    
  }
}