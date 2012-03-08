package com.tscp.mvne.service;

import com.tscp.mvne.billing.Account;
import com.tscp.mvne.billing.system.BillingSystem;
import com.tscp.mvne.customer.Customer;
import com.tscp.mvne.customer.contact.ContactInfo;
import com.tscp.mvne.customer.dao.AddressDao;
import com.tscp.mvne.customer.dao.ContactDao;
import com.tscp.mvne.jms.request.account.AccountRequest;
import com.tscp.mvne.jms.request.account.AccountRequestType;
import com.tscp.mvne.jms.request.account.ServiceInstanceRequest;
import com.tscp.mvne.network.NetworkInfo;
import com.tscp.mvne.network.NetworkSystem;
import com.tscp.mvne.service.exception.AccountRequestException;
import com.tscp.mvne.system.provider.BillingSystemProvider;
import com.tscp.mvne.system.provider.NetworkSystemProvider;

public class AccountService {
  protected static final BillingSystem billingSystem = BillingSystemProvider.getInstance();
  protected static final NetworkSystem networkSystem = NetworkSystemProvider.getInstance();
  protected ContactDao contactDao = new ContactDao();
  protected AddressDao addressDao = new AddressDao();

  public void saveContactInfo(ContactInfo contactInfo) {
    contactDao.save(contactInfo.getContact());
    addressDao.save(contactInfo.getAddress());
  }

  public Account doAccountRequest(AccountRequest request) throws AccountRequestException {
    switch (request.getRequestType()) {
    case SHELL:
      return createShellAccount(request);
    default:
      break;
    }
    return new Account();
  }

  public Account createShellAccount(AccountRequest request) throws AccountRequestException {
    Customer customer = new Customer(request.getCustomerId());
    Account account = makeAccount(request);
    int accountNumber = billingSystem.createAccount(account);
    if (accountNumber > 0) {
      customer.addCustAccts(account);
    } else {
      String msg = "No account number was returned from the billing system.";
      throw new AccountRequestException(AccountRequestType.SHELL, msg);
    }
    return account;
  }

  public Account makeAccount(AccountRequest shellAccountRequest) {
    Account account = new Account();
    account.setFirstname(shellAccountRequest.getContactInfo().getContact().getFirstName());
    account.setMiddlename(shellAccountRequest.getContactInfo().getContact().getMiddleName());
    account.setLastname(shellAccountRequest.getContactInfo().getContact().getLastName());
    account.setContact_email(shellAccountRequest.getContactInfo().getContact().getEmail());
    account.setContact_number(shellAccountRequest.getContactInfo().getContact().getPhoneNumber());
    account.setContact_address1(shellAccountRequest.getContactInfo().getAddress().getAddress1());
    account.setContact_address2(shellAccountRequest.getContactInfo().getAddress().getAddress2());
    account.setContact_city(shellAccountRequest.getContactInfo().getAddress().getCity());
    account.setContact_state(shellAccountRequest.getContactInfo().getAddress().getState());
    account.setContact_zip(shellAccountRequest.getContactInfo().getAddress().getZip());
    return account;
  }

}
