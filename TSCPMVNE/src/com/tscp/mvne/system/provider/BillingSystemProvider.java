package com.tscp.mvne.system.provider;

import com.tscp.mvne.billing.system.BillingSystem;

public class BillingSystemProvider {
  private static final BillingSystem instance = new BillingSystem();

  protected BillingSystemProvider() {
    // prevent instantiation
  }
  
  public static final BillingSystem getInstance() {
    return instance;
  }

}
