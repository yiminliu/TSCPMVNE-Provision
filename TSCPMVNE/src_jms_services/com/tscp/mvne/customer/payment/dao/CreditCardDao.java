package com.tscp.mvne.customer.payment.dao;

import com.tscp.mvne.customer.payment.method.CustomerCreditCard;
import com.tscp.mvne.hibernate.AbstractHibernateDao;

public class CreditCardDao extends AbstractHibernateDao<CustomerCreditCard> {

  public CreditCardDao() {
    setClazz(CustomerCreditCard.class);
  }

}
