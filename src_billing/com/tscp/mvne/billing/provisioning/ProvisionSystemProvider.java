package com.tscp.mvne.billing.provisioning;

public class ProvisionSystemProvider {
  private static final ProvisionSystem instance = new ProvisionSystem();

  protected ProvisionSystemProvider() {
    // prevent instantiation
  }

  public static final ProvisionSystem getInstance() {
    return instance;
  }

}