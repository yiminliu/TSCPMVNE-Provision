package com.tscp.mvne.payment.transaction;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Transient;

@Embeddable
public class MerchantResponse implements Serializable {
  private static final long serialVersionUID = 964933990788894691L;
  private String confirmationCode;
  private String message;
  private Date date;

  @Column(name = "pmt_unit_confirmation")
  public String getConfirmationCode() {
    return confirmationCode;
  }

  public void setConfirmationCode(String confirmationCode) {
    this.confirmationCode = confirmationCode;
  }

  @Column(name = "pmt_unit_msg")
  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  @Column(name = "pmt_unit_date")
  public Date getDate() {
    return date;
  }

  public void setDate(Date date) {
    this.date = date;
  }

  @Transient
  public boolean isSuccess() {
    return message != null && message.toLowerCase().contains("success");
  }

}
