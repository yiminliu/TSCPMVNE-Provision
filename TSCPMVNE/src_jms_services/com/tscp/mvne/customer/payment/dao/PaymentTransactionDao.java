package com.tscp.mvne.customer.payment.dao;

import com.tscp.mvne.hibernate.AbstractHibernateDao;
import com.tscp.mvne.payment.transaction.CustPaymentTransaction;

public class PaymentTransactionDao extends AbstractHibernateDao<CustPaymentTransaction> {

  public PaymentTransactionDao() {
    setClazz(CustPaymentTransaction.class);
  }
}
