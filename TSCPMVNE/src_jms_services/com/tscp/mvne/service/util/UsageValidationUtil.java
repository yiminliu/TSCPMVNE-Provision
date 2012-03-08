package com.tscp.mvne.service.util;

import java.util.Date;
import java.util.List;

import com.tscp.mvne.billing.dao.UsageDetail;
import com.tscp.mvne.customer.Customer;
import com.tscp.mvne.network.exception.UsageDetailAccessException;

public class UsageValidationUtil {

  public static boolean requiresMRC(int customerId, int accountNumber, String mdn, Date lastActiveDate)
      throws UsageDetailAccessException {
    lastActiveDate = getLastActiveDate(customerId, accountNumber, mdn);
    Date currentDate = new Date();
    return lastActiveDate != null && lastActiveDate.before(currentDate);
  }

  private static boolean isAccessFee(UsageDetail usageDetail) {
    return usageDetail.getUsageType().equalsIgnoreCase("Access Fee");
  }

  public static Date getLastActiveDate(int customerId, int accountNumber, String mdn) throws UsageDetailAccessException {
    Date lastActiveDate = null;
    Customer customer = new Customer(customerId);
    List<UsageDetail> usageDetails = customer.getChargeHistory(accountNumber, mdn);
    if (usageDetails != null && !usageDetails.isEmpty()) {
      for (UsageDetail usageDetail : usageDetails) {
        if (isAccessFee(usageDetail)) {
          lastActiveDate = usageDetail.getEndTime();
          break;
        }
      }
    } else {
      throw new UsageDetailAccessException("Could not retrieve usage detail for customer " + customerId);
    }
    return lastActiveDate;
  }
}
