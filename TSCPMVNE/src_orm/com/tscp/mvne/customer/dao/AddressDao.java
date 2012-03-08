package com.tscp.mvne.customer.dao;

import com.tscp.mvne.customer.contact.CustomerAddress;
import com.tscp.mvne.hibernate.AbstractHibernateDao;

public class AddressDao extends AbstractHibernateDao<CustomerAddress> {

  public AddressDao() {
    setClazz(CustomerAddress.class);
  }
  
}
