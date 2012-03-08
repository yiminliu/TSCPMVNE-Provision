package com.tscp.mvne.payment.transaction;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * ORM implementation of the pmt_trans table. It is oddly named to avoid
 * conflicts with legacy classes.
 * 
 * @author Tachikoma
 * 
 */
@Entity
@Table(name = "pmt_trans")
public class CustPaymentTransaction implements Serializable {
  private static final long serialVersionUID = 9134040653969001734L;
  private int transactionId;
  private String sessionId;
  private int accountNumber;
  private int attemptNumber = 1;
  private int customerId;
  private double amount;
  private Date date = new Date();
  private PaymentSource paymentSource = new PaymentSource();
  private MerchantResponse merchantResponse = new MerchantResponse();
  private BillingResponse billingResponse = new BillingResponse();

  @Id
  @Column(name = "trans_id")
  @GeneratedValue(strategy = GenerationType.AUTO, generator = "pmt_trans_seq_gen")
  @SequenceGenerator(name = "pmt_trans_seq_gen", sequenceName = "PMT_TRANS_TRANS_ID_SEQ")
  public int getTransactionId() {
    return transactionId;
  }

  public void setTransactionId(int transactionId) {
    this.transactionId = transactionId;
  }

  @Column(name = "cust_id")
  public int getCustomerId() {
    return customerId;
  }

  public void setCustomerId(int customerId) {
    this.customerId = customerId;
  }

  @Column(name = "session_id")
  public String getSessionId() {
    return sessionId;
  }

  public void setSessionId(String sessionId) {
    this.sessionId = sessionId;
  }

  @Column(name = "account_no")
  public int getAccountNumber() {
    return accountNumber;
  }

  public void setAccountNumber(int accountNumber) {
    this.accountNumber = accountNumber;
  }

  @Column(name = "attempt_no")
  public int getAttemptNumber() {
    return attemptNumber;
  }

  public void setAttemptNumber(int attemptNumber) {
    this.attemptNumber = attemptNumber;
  }

  @Column(name = "pmt_amount")
  public double getAmount() {
    return amount;
  }

  public void setAmount(double amount) {
    this.amount = amount;
  }

  @Embedded
  public PaymentSource getPaymentSource() {
    return paymentSource;
  }

  public void setPaymentSource(PaymentSource paymentSource) {
    this.paymentSource = paymentSource;
  }

  @Embedded
  public MerchantResponse getMerchantResponse() {
    return merchantResponse;
  }

  public void setMerchantResponse(MerchantResponse merchantResponse) {
    this.merchantResponse = merchantResponse;
  }

  @Embedded
  public BillingResponse getBillingResponse() {
    return billingResponse;
  }

  public void setBillingResponse(BillingResponse billingResponse) {
    this.billingResponse = billingResponse;
  }

  @Column(name = "pmt_trans_date")
  public Date getDate() {
    return date;
  }

  public void setDate(Date date) {
    this.date = date;
  }

}