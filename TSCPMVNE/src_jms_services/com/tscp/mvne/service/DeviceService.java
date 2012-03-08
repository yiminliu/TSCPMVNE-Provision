package com.tscp.mvne.service;

import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.classic.Session;

import com.tscp.mvne.billing.ServiceInstance;
import com.tscp.mvne.billing.exception.BillingException;
import com.tscp.mvne.billing.system.BillingSystem;
import com.tscp.mvne.customer.Customer;
import com.tscp.mvne.customer.dao.DeviceAssociation;
import com.tscp.mvne.customer.dao.DeviceInfo;
import com.tscp.mvne.device.DeviceException;
import com.tscp.mvne.hibernate.HibernateUtil;
import com.tscp.mvne.network.NetworkInfo;
import com.tscp.mvne.system.provider.BillingSystemProvider;

public class DeviceService {
  protected static final BillingSystem billingSystem = BillingSystemProvider.getInstance();

  public DeviceInfo getDeviceInfo(int customerId, int accountNumber) {
    Session session = HibernateUtil.getSessionFactory().getCurrentSession();
    session.beginTransaction();
    Query q = session.getNamedQuery("fetch_device_info");
    q.setParameter("in_cust_id", customerId);
    q.setParameter("in_device_id", 0);
    q.setParameter("in_account_no", accountNumber);
    DeviceInfo deviceInfo = (DeviceInfo) q.uniqueResult();
    session.getTransaction().rollback();
    return deviceInfo;
  }

  protected DeviceAssociation getDeviceAssociation(Customer customer, DeviceInfo deviceInfo) throws DeviceException {
    List<DeviceAssociation> deviceAssociationList = customer.retrieveDeviceAssociationList(deviceInfo.getDeviceId());
    if (deviceAssociationList != null && !deviceAssociationList.isEmpty()) {
      for (DeviceAssociation deviceAssociation : deviceAssociationList) {
        return deviceAssociation;
      }
    }
    throw new DeviceException("getDeviceAssociation", "No historical device association found for Customer " + customer.getId()
        + " and Device " + deviceInfo.getDeviceId());
  }

  protected void updateDeviceAssociation(DeviceInfo deviceInfo, int deviceStatus, NetworkInfo networkInfo) {
    try {
      List<ServiceInstance> services = billingSystem.getServiceInstances(deviceInfo.getAccountNo());
      if (services != null) {
        for (ServiceInstance serviceInstance : services) {
          if (serviceInstance.isEqual(networkInfo)) {
            DeviceAssociation deviceAssociation = new DeviceAssociation();
            deviceAssociation.setDeviceId(deviceInfo.getDeviceId());
            deviceAssociation.setSubscrNo(serviceInstance.getSubscriberNumber());
            deviceAssociation.save();
            if (deviceInfo.getDeviceStatusId() != deviceStatus) {
              deviceInfo.setDeviceStatusId(deviceStatus);
              deviceInfo.setEffectiveDate(new Date());
              deviceInfo.save();
            }
          }
          break;
        }
      }
    } catch (BillingException e) {
      throw new DeviceException("updateDeviceAssociation", "Could not retrieve service instances for account "
          + deviceInfo.getAccountNo());
    }
  }

}
