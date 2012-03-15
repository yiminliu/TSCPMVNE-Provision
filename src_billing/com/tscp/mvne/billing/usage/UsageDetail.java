package com.tscp.mvne.billing.usage;

import java.io.Serializable;
import java.util.Date;

public class UsageDetail implements Serializable {
  private static final long serialVersionUID = 6998103452809873332L;
  private Date dateAndTime;
  private Date startTime;
  private Date endTime;
  private String usageType;
  private String rate;
  private String usageAmount;
  private String dollarAmount;
  private String discount;
  private String notes;
  private double balance;

  public UsageDetail() {
    // do nothing
  }

  public Date getDateAndTime() {
    return dateAndTime;
  }

  public void setDateAndTime(Date dateAndTime) {
    this.dateAndTime = dateAndTime;
  }

  public String getRate() {
    return rate;
  }

  public void setRate(String rate) {
    this.rate = rate;
  }

  public Date getStartTime() {
    return startTime;
  }

  public void setStartTime(Date startTime) {
    this.startTime = startTime;
  }

  public Date getEndTime() {
    return endTime;
  }

  public void setEndTime(Date endTime) {
    this.endTime = endTime;
  }

  public String getNotes() {
    return notes;
  }

  public void setNotes(String notes) {
    this.notes = notes;
  }

  public void setUsageAmount(String usageAmount) {
    this.usageAmount = usageAmount;
  }

  public String getUsageAmount() {
    return usageAmount;
  }

  public String getDollarAmount() {
    return dollarAmount;
  }

  public void setDollarAmount(String dollarAmount) {
    this.dollarAmount = dollarAmount;
  }

  public void setUsageType(String usageType) {
    this.usageType = usageType;
  }

  public String getUsageType() {
    return usageType;
  }

  public double getBalance() {
    return balance;
  }

  public void setBalance(double balance) {
    this.balance = balance;
  }

  public String getDiscount() {
    return discount;
  }

  public void setDiscount(String discount) {
    this.discount = discount;
  }

  @Override
  public String toString() {
    return "UsageDetail [dateAndTime=" + dateAndTime + ", usageType=" + usageType + ", rate=" + rate + ", usageAmount="
        + usageAmount + ", dollarAmount=" + dollarAmount + ", discount=" + discount + ", startTime=" + startTime + ", endTime="
        + endTime + ", notes=" + notes + ", balance=" + balance + "]";
  }

}
