package com.tscp.mvne;

import javax.xml.ws.WebServiceException;

import com.tscp.mvne.billing.Account;
import com.tscp.mvne.billing.ServiceInstance;
import com.tscp.mvne.contract.exception.ContractException;
import com.tscp.mvne.customer.Customer;
import com.tscp.mvne.logger.LoggerHelper;
import com.tscp.mvne.network.NetworkInfo;

public interface TscpMvne {

  public void init();

  public NetworkInfo getNetworkInfo(String esn, String mdn);

  public NetworkInfo reserveMDN() throws WebServiceException;

  public NetworkInfo activateService(Customer customer, NetworkInfo networkinfo) throws WebServiceException;

  public void suspendService(ServiceInstance serviceInstance) throws WebServiceException;

  public void restoreService(ServiceInstance serviceInstance) throws WebServiceException;

  public void disconnectService(ServiceInstance serviceInstance) throws WebServiceException;

  public Account createBillingAccount(Customer cust, Account account) throws WebServiceException;

  public Account createServiceInstance(Account account, ServiceInstance serviceinstance) throws WebServiceException;

  public void refundPayment(int account_no, String amount, int  trackingId, String reverseBy) throws ContractException;


}
