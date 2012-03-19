package com.tscp.mvne.customer;

public class CustUtil {

  public static void checkId(int custId) throws CustomerException {
    if (custId == 0) {
      throw new CustomerException("Cust ID is not set");
    }
  }

}
