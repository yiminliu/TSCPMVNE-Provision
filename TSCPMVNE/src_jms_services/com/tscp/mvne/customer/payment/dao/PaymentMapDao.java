package com.tscp.mvne.customer.payment.dao;

import com.tscp.mvne.customer.payment.PaymentMap;
import com.tscp.mvne.hibernate.AbstractHibernateDao;

public class PaymentMapDao extends AbstractHibernateDao<PaymentMap> {

  public PaymentMapDao() {
    setClazz(PaymentMap.class);
  }

}
