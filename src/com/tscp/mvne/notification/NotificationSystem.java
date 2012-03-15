package com.tscp.mvne.notification;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;
import java.util.Vector;

import javax.mail.internet.InternetAddress;

@Deprecated
public class NotificationSystem {
  public static String smtpHost;
  static String inputPropertyFile = "com/tscp/mvne/config/connection.tscpmvne.properties";
  Properties props;

  InternetAddress from;
  Vector<InternetAddress> bccList;

  public static String truconnectManageSite;
  public static String truconnectSupportSite;
  public static String truconnectTermsAndConditionsSite;

  public NotificationSystem() {
    init();
  }

  public void init() {
    ClassLoader cl = NotificationSystem.class.getClassLoader();
    InputStream in = cl.getResourceAsStream(inputPropertyFile);
    props = new Properties();
    try {
      props.load(in);

      smtpHost = props.getProperty("email.smtphost");
      truconnectManageSite = props.getProperty("truconnect.manage.site");
      truconnectSupportSite = props.getProperty("truconnect.support.site");
      truconnectTermsAndConditionsSite = props.getProperty("truconnect.terms.and.conditions.site");
      from = new InternetAddress("no-reply@truconnect.com", "TruConnect");
      InternetAddress address = new InternetAddress("trualert@truconnect.com", "TruAlert");
      bccList = new Vector<InternetAddress>();
      bccList.add(address);
    } catch (IOException ioe) {
      ioe.printStackTrace();
    }
  };

  public InternetAddress getFrom() {
    return from;
  }

  public List<InternetAddress> getBccList() {
    return bccList;
  }

}