package com.tscp.mvne.system.provider;

import com.tscp.mvne.notification.NotificationSystemImpl;

public class NotificationSystemProvider {
  private static final NotificationSystemImpl instance = new NotificationSystemImpl();
  
  protected NotificationSystemProvider() {
    // prevent instantiation
  }
  
  public static final NotificationSystemImpl getInstance() {
    return instance;
  }
  
}
