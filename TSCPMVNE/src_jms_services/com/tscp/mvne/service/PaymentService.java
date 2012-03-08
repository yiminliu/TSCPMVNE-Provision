package com.tscp.mvne.service;

import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Transaction;
import org.hibernate.classic.Session;

import com.tscp.mvne.billing.Account;
import com.tscp.mvne.billing.api.Payment;
import com.tscp.mvne.billing.api.PaymentHolder;
import com.tscp.mvne.billing.exception.BillingException;
import com.tscp.mvne.billing.system.BillingSystem;
import com.tscp.mvne.customer.payment.dao.PaymentTransactionDao;
import com.tscp.mvne.hibernate.HibernateUtil;
import com.tscp.mvne.jms.request.payment.PaymentRequest;
import com.tscp.mvne.jms.request.payment.PaymentRequestResponse;
import com.tscp.mvne.payment.dao.PaymentUnitResponse;
import com.tscp.mvne.payment.transaction.CustPaymentTransaction;
import com.tscp.mvne.payment.transaction.PaymentSource;
import com.tscp.mvne.system.provider.BillingSystemProvider;

//TODO should catch and throw PaymentRequestException
public class PaymentService {
  protected static final BillingSystem billingSystem = BillingSystemProvider.getInstance();
  protected PaymentTransactionDao transactionDao = new PaymentTransactionDao();

  public PaymentRequestResponse doSubmitToMerchant(PaymentRequest request) {
    CustPaymentTransaction transaction = new CustPaymentTransaction();
    transaction.setCustomerId(request.getCustomerId());
    transaction.setSessionId(request.getSessionId());
    transaction.setPaymentSource(new PaymentSource());
    transaction.getPaymentSource().setPaymentId(request.getCreditCard().getPaymentId());
    transaction.getPaymentSource().setSource(request.getCreditCard().getCardNumer());
    transaction.getPaymentSource().setMethod("Visa");
    transaction.setAmount(request.getAmount());
    transaction.setAccountNumber(request.getAccountNumber());
    saveInitialTransaction(transaction);
    submitPayment(transaction);
    PaymentRequestResponse result = new PaymentRequestResponse();
    result.setTransaction(transaction);
    result.setType(request.getType());
    return result;
    // transaction = submitPayment(transaction);
  }

  private CustPaymentTransaction submitPayment(CustPaymentTransaction transaction) {
    PaymentUnitResponse response = null;
    Session session = HibernateUtil.getSessionFactory().getCurrentSession();
    Transaction tx = session.beginTransaction();
    Query q = session.getNamedQuery("sbt_pmt_info");
    q.setParameter("in_cust_id", transaction.getCustomerId());
    q.setParameter("in_pmt_id", transaction.getPaymentSource().getPaymentId());
    q.setParameter("in_pymntamt", formatDollar(transaction.getAmount()));
    for (PaymentUnitResponse pur : (List<PaymentUnitResponse>) q.list()) {
      response = pur;
    }
    tx.commit();
    if (response == null) {
      response = new PaymentUnitResponse();
      response.setConfcode("-1");
      response.setConfdescr("No response returned from payment unit");
      response.setAuthcode("System generated error input");
    }
    saveMerchantResponse(transaction, response);
    return transaction;
  }

  private void saveInitialTransaction(CustPaymentTransaction transaction) {
    transactionDao.save(transaction);
  }

  private void saveMerchantResponse(CustPaymentTransaction transaction, PaymentUnitResponse response) {
    transaction.getMerchantResponse().setConfirmationCode(response.getConfirmationString());
    transaction.getMerchantResponse().setDate(new Date());
    transaction.getMerchantResponse().setMessage(response.getConfdescr() + " AuthCode::" + response.getAuthcode());
    transactionDao.update(transaction);
  }

  public synchronized int doSubmitToBilling(Account account, CustPaymentTransaction transaction) throws BillingException {
    try {
      billingSystem.addPayment(account, formatDollar(transaction.getAmount()).replace(".", ""));
      return saveBillingResponse(account, transaction);
    } catch (Exception e) {
      throw new BillingException(e.getMessage());
    }
  }

  private synchronized int saveBillingResponse(Account account, CustPaymentTransaction transaction) {
    List<PaymentHolder> paymentList = billingSystem.getCompletePaymentHistory(account);
    Payment payment = getLatestPayment(paymentList);
    if (payment != null) {
      transaction.getBillingResponse().setTrackingId(payment.getTrackingId());
    } else {
      transaction.getBillingResponse().setTrackingId(-1);
    }
    transaction.getBillingResponse().setDate(new Date());
    transactionDao.update(transaction);
    return transaction.getBillingResponse().getTrackingId();
  }

  private synchronized Payment getLatestPayment(List<PaymentHolder> paymentList) {
    try {
      Payment payment = null;
      for (PaymentHolder paymentHolder : paymentList) {
        if (payment == null) {
          payment = paymentHolder.getPayment();
        }
        if (payment.getTrackingId() <= paymentHolder.getPayment().getTrackingId()) {
          payment = paymentHolder.getPayment();
        }
      }
      return payment;
    } catch (ArrayIndexOutOfBoundsException index_ex) {
      return null;
    } catch (NullPointerException np_ex) {
      return null;
    }
  }

  private String formatDollar(Double amount) {
    String result = Double.toString(amount);
    while (result.substring(result.indexOf(".") + 1).length() < 2) {
      result += "0";
    }
    return result;
  }
}
