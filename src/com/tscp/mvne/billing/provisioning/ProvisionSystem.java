package com.tscp.mvne.billing.provisioning;

import java.util.List;
import java.util.Vector;

import com.telscape.billingserviceinterface.ArrayOfComponentHolder;
import com.telscape.billingserviceinterface.ArrayOfMessageHolder;
import com.telscape.billingserviceinterface.ArrayOfPackage;
import com.telscape.billingserviceinterface.ArrayOfPackageHolder;
import com.telscape.billingserviceinterface.ArrayOfPkgComponent;
import com.telscape.billingserviceinterface.ArrayOfServiceHolder;
import com.telscape.billingserviceinterface.ArrayOfValueHolder;
import com.telscape.billingserviceinterface.BillingService;
import com.telscape.billingserviceinterface.ComponentHolder;
import com.telscape.billingserviceinterface.MessageHolder;
import com.telscape.billingserviceinterface.PackageHolder;
import com.telscape.billingserviceinterface.PkgComponent;
import com.telscape.billingserviceinterface.ServiceHolder;
import com.telscape.billingserviceinterface.ValueHolder;
import com.tscp.mvne.billing.Account;
import com.tscp.mvne.billing.exception.BillingServerException;
import com.tscp.mvne.billing.exception.ProvisionException;
import com.tscp.mvne.billing.service.BillService;
import com.tscp.mvne.config.PROVISION;

/**
 * Business rules currently only allow for one service per account to track
 * usage. Therefore there should only be one active service per account. If this
 * changes then much more complicated logic will have to be implemented across
 * the application.
 * 
 */
public class ProvisionSystem extends BillService {
  private static final String USERNAME = "TSCPMVNE.ProvisioningSystem";

  /**
   * Adds a component to the service and package and activates it along with all
   * other components in the package.
   * 
   * @param accountNo
   * @param externalId
   * @param pkg
   * @param component
   * @throws ProvisionException
   */
  public void addComponent(int accountNo, String externalId, Package pkg, Component component) throws ProvisionException {
    ProvisionUtil.checkAccountNumber(accountNo);
    ProvisionUtil.checkExternalId(externalId);
    ProvisionUtil.checkPackage(pkg);
    ArrayOfPkgComponent componentList = ProvisionUtil.buildComponentList(externalId, pkg, component);
    ArrayOfMessageHolder messageArray = port.addComponent(USERNAME, componentList);
    try {
      ProvisionUtil.checkResponse(messageArray);
    } catch (BillingServerException e) {
      throw new ProvisionException("Error adding component to account " + accountNo, e);
    }
  }

  /**
   * Adds the given package to the account and returns the package with it's
   * instanceId populated.
   * 
   * @param accountNumber
   * @param pkg
   * @return
   * @throws ProvisionException
   */
  public Package addPackage(int accountNumber, Package pkg) throws ProvisionException {
    ProvisionUtil.checkAccountNumber(accountNumber);
    ArrayOfPackage packages = ProvisionUtil.buildPackageList(accountNumber, pkg);
    if (pkg == null) {
      pkg = new Package();
      pkg.setAccountNumber(accountNumber);
    }
    ArrayOfValueHolder valueArray = port.addPackage(USERNAME, packages);
    try {
      ValueHolder value = ProvisionUtil.checkResponse(valueArray);
      pkg.setInstanceId(Integer.parseInt(value.getValue().trim()));
      pkg.setInstanceIdServ(Integer.parseInt(value.getValue2().trim()));
      return pkg;
    } catch (BillingServerException e) {
      throw new ProvisionException("Error adding package " + packages.getPackage().get(0).getPackageId() + " to Account " + accountNumber, e);
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
   * @param accountNo
   * @param externalId
   * @throws ProvisionException
   */
  public void addServiceInstance(int accountNo, String externalId) throws ProvisionException {
    ProvisionUtil.checkAccountNumber(accountNo);
    ProvisionUtil.checkExternalId(externalId);
    Account account = getAccount(accountNo);
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
    MessageHolder message = port.addService(USERNAME, billingService);
    try {
      ProvisionUtil.checkResponse(message);
    } catch (BillingServerException e) {
      throw new ProvisionException("Error adding ServiceInstance " + externalId + " to account " + accountNo, e);
    }
  }

  /**
   * Adds a single component to the package and service without activating all
   * other components in the package.
   * 
   * @param accountNumber
   * @param externalId
   * @param pkg
   * @param component
   * @throws ProvisionException
   */
  public void addSingleComponent(int accountNumber, String externalId, Package pkg, Component component) throws ProvisionException {
    ProvisionUtil.checkAccountNumber(accountNumber);
    ProvisionUtil.checkExternalId(externalId);
    ProvisionUtil.checkPackage(pkg);
    ArrayOfPkgComponent componentList = ProvisionUtil.buildComponentList(externalId, pkg, component);
    ArrayOfMessageHolder messageArray = port.insertSingleComponent(USERNAME, componentList);
    try {
      ProvisionUtil.checkResponse(messageArray);
    } catch (BillingServerException e) {
      throw new ProvisionException("Error adding component to account " + accountNumber, e);
    }
  }

  public List<Component> getActiveComponents(int accountNumber, String externalId) throws ProvisionException {
    try {
      List<Component> results = new Vector<Component>();
      ArrayOfComponentHolder components = port.getListActiveComponent(USERNAME, Integer.toString(accountNumber), externalId);
      if (components != null && components.getComponentHolder() != null && !components.getComponentHolder().isEmpty()) {
        for (ComponentHolder componentHolder : components.getComponentHolder()) {
          results.add(ProvisionUtil.buildComponent(componentHolder.getComponent()));
        }
      }
      return results;
    } catch (Exception e) {
      throw new ProvisionException("Unable to get components from Billing Server for account " + accountNumber + " externalId " + externalId);
    }
  }

  public List<Package> getActivePackages(int accountNumber) throws ProvisionException {
    try {
      List<Package> results = new Vector<Package>();
      ArrayOfPackageHolder packages = port.getListActivePackages(USERNAME, Integer.toString(accountNumber));
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

  public List<ServiceInstance> getActiveServices(int accountNumber) throws ProvisionException {
    try {
      List<ServiceInstance> results = new Vector<ServiceInstance>();
      ArrayOfServiceHolder services = port.getActiveService(USERNAME, Integer.toString(accountNumber));
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

  public void removeComponent(int accountNo, String externalId, Package pkg, Component component) throws ProvisionException {
    ProvisionUtil.checkAccountNumber(accountNo);
    ProvisionUtil.checkExternalId(externalId);
    ProvisionUtil.checkPackage(pkg);
    ArrayOfPkgComponent componentList = ProvisionUtil.buildComponentList(externalId, pkg, component);
    for (PkgComponent pkgComponent : componentList.getPkgComponent()) {
      pkgComponent.setDiscReason(DISC_REASON);
      pkgComponent.setDiscDate(ProvisionUtil.getCalendar());
    }
    ArrayOfMessageHolder messageArray = port.disconnectComponent(USERNAME, componentList);
    try {
      ProvisionUtil.checkResponse(messageArray);
    } catch (BillingServerException e) {
      throw new ProvisionException("Error removing component from Account " + accountNo, e);
    }
  }

  protected void removePackage(int accountNo, Package pkg) throws ProvisionException {
    ProvisionUtil.checkAccountNumber(accountNo);
    ProvisionUtil.checkPackage(pkg);
    com.telscape.billingserviceinterface.Package billingPackage = ProvisionUtil.buildBillingPackage(accountNo, pkg);
    billingPackage.setDiscReason(DISC_REASON);
    billingPackage.setDiscDate(ProvisionUtil.getCalendar());
    ArrayOfPackage packages = new ArrayOfPackage();
    packages.getPackage().add(billingPackage);
    ArrayOfMessageHolder messageArray = port.disconnectPackage(USERNAME, packages);
    try {
      ProvisionUtil.checkResponse(messageArray);
    } catch (BillingServerException e) {
      throw new ProvisionException("Could not remove package " + billingPackage.getPackageInstanceId() + " on account " + accountNo, e);
    }
  }

  public void removeServiceInstance(int accountNo, String externalId) throws ProvisionException {
    ProvisionUtil.checkAccountNumber(accountNo);
    ProvisionUtil.checkExternalId(externalId);
    MessageHolder message = port.disconnectServicePackages(USERNAME, Integer.toString(accountNo), externalId, PROVISION.SERVICE.EXTERNAL_ID_TYPE, ProvisionUtil
        .getCalendar(), DISC_REASON);
    try {
      ProvisionUtil.checkResponse(message);
    } catch (BillingServerException e) {
      throw new ProvisionException("Could not remove service " + externalId + " on account " + accountNo, e);
    }

  }

}