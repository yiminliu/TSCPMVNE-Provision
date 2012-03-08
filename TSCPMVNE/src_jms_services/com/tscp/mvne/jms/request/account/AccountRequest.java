package com.tscp.mvne.jms.request.account;

import com.tscp.mvne.customer.contact.ContactInfo;
import com.tscp.mvne.jms.request.Request;

public class AccountRequest extends Request {
  private static final long serialVersionUID = 83408639010891244L;
  private int CustomerId;
  private int AccountNumber;
  private AccountRequestType requestType;
  private ContactInfo contactInfo = new ContactInfo();

  public int getCustomerId() {
    return CustomerId;
  }

  public void setCustomerId(int customerId) {
    CustomerId = customerId;
  }

  public int getAccountNumber() {
    return AccountNumber;
  }

  public void setAccountNumber(int accountNumber) {
    AccountNumber = accountNumber;
  }

  public AccountRequestType getRequestType() {
    return requestType;
  }

  public void setRequestType(AccountRequestType requestType) {
    this.requestType = requestType;
  }

  public ContactInfo getContactInfo() {
    return contactInfo;
  }

  public void setContactInfo(ContactInfo contactInfo) {
    this.contactInfo = contactInfo;
  }

  @Override
  public String toString() {
    return "AccountRequest [CustomerId=" + CustomerId + ", AccountNumber=" + AccountNumber + ", requestType=" + requestType
        + ", contactInfo=" + contactInfo + ", attemptNumber=" + attemptNumber + ", history=" + history + "]";
  }

}