package com.tscp.mvne.billing;

import java.io.Serializable;
import java.util.Date;

public class ServiceInstance implements Serializable {
  private static final long serialVersionUID = 8814958679258867200L;
  private int subscriberNumber;
  private int externalIdType;
  private String externalId;
  private Date activeDate;
  private Date inactiveDate;

  public ServiceInstance() {
    // do nothing
  }

  public int getSubscriberNumber() {
    return subscriberNumber;
  }

  public void setSubscriberNumber(int subscriberNumber) {
    this.subscriberNumber = subscriberNumber;
  }

  public int getExternalIdType() {
    return externalIdType;
  }

  public void setExternalIdType(int externalIdType) {
    this.externalIdType = externalIdType;
  }

  public String getExternalId() {
    return externalId;
  }

  public void setExternalId(String externalId) {
    this.externalId = externalId;
  }

  public Date getActiveDate() {
    return activeDate;
  }

  public void setActiveDate(Date activeDate) {
    this.activeDate = activeDate;
  }

  public Date getInactiveDate() {
    return inactiveDate;
  }

  public void setInactiveDate(Date inactiveDate) {
    this.inactiveDate = inactiveDate;
  }

  @Override
  public String toString() {
    return "ServiceInstance [subscriberNumber=" + subscriberNumber + ", externalIdType=" + externalIdType + ", externalId="
        + externalId + ", activeDate=" + activeDate + ", inactiveDate=" + inactiveDate + "]";
  }

}
