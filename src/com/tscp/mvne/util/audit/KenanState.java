package com.tscp.mvne.util.audit;

import com.tscp.mvne.billing.provisioning.Component;
import com.tscp.mvne.billing.provisioning.Package;
import com.tscp.mvne.billing.provisioning.ServiceInstance;

public class KenanState {
  private String email;
  private ServiceInstance serviceInstance;
  private Package pkg;
  private Component component;

  public KenanState() {

  }

  public KenanState(String email, ServiceInstance serviceInstance, Package pkg, Component component) {
    this.email = email;
    this.serviceInstance = serviceInstance;
    this.pkg = pkg;
    this.component = component;
  }

  public ServiceInstance getServiceInstance() {
    return serviceInstance;
  }

  public void setServiceInstance(ServiceInstance serviceInstance) {
    this.serviceInstance = serviceInstance;
  }

  public Package getPkg() {
    return pkg;
  }

  public void setPkg(Package pkg) {
    this.pkg = pkg;
  }

  public Component getComponent() {
    return component;
  }

  public void setComponent(Component component) {
    this.component = component;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }
}
