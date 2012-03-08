package com.tscp.mvne.customer.payment.dao;

import com.tscp.mvne.customer.payment.PaymentInfo;
import com.tscp.mvne.hibernate.AbstractHibernateDao;

public class PaymentInfoDao extends AbstractHibernateDao<PaymentInfo> {

  public PaymentInfoDao() {
    setClazz(PaymentInfo.class);
  }

}
