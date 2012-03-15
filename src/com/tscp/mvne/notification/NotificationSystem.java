package com.tscp.mvne.notification;

import java.io.UnsupportedEncodingException;
import java.util.Vector;

import javax.mail.internet.InternetAddress;

@Deprecated
/**
 * This functionality of setting the "from" and "bccList" should be taken care of in the notification MDB using JMS
 * @author Tachikoma
 *
 */
public class NotificationSystem {
  public static final InternetAddress from = buildFrom();
  public static final Vector<InternetAddress> bccList = buildBccList();

  protected static InternetAddress buildFrom() {
    try {
      return new InternetAddress("no-reply@truconnect.com", "TruConnect");
    } catch (UnsupportedEncodingException e) {
      return new InternetAddress();
    }
  }

  protected static Vector<InternetAddress> buildBccList() {
    Vector<InternetAddress> bccList = new Vector<InternetAddress>();
    try {
      bccList.add(new InternetAddress("trualert@truconnect.com", "TruAlert"));
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    }
    return bccList;
  }

}