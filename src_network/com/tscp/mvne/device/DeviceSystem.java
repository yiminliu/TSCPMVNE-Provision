package com.tscp.mvne.device;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.classic.Session;

import com.tscp.mvne.hibernate.HibernateUtil;

public class DeviceSystem {

  public List<Device> getDevices(int custId, int deviceId, int accountNo) {
    Session session = HibernateUtil.getSessionFactory().getCurrentSession();
    session.beginTransaction();
    Query q = session.getNamedQuery("fetch_device_info");
    q.setParameter("in_cust_id", custId);
    q.setParameter("in_device_id", deviceId);
    q.setParameter("in_account_no", accountNo);
    List<Device> devices = q.list();
    session.getTransaction().rollback();
    return devices;
  }

}