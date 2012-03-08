package com.tscp.mvne.payment.transaction;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class BillingResponse implements Serializable {
  private static final long serialVersionUID = -7166806091492454529L;
  private int trackingId;
  private Date date;

  @Column(name = "billing_tracking_id")
  public int getTrackingId() {
    return trackingId;
  }

  public void setTrackingId(int trackingId) {
    this.trackingId = trackingId;
  }

  @Column(name = "billing_unit_date")
  public Date getDate() {
    return date;
  }

  public void setDate(Date date) {
    this.date = date;
  }

}
