package com.tscp.mvne.billing;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

public class Package implements Serializable {
  private static final long serialVersionUID = -5062081445629324826L;
  private int id;
  private int instanceId;
  private String instanceIdServ;
  private int accountNumber;
  private String name;
  private Date activeDate;
  private Date inactiveDate;
  private Collection<Component> componentList;

  public Package() {
    // do nothing
  }

  public int getId() {
    return id;
  }

  public void setId(int packageid) {
    this.id = packageid;
  }

  public int getInstanceId() {
    return instanceId;
  }

  public void setInstanceId(int package_instance_id) {
    this.instanceId = package_instance_id;
  }

  public String getInstanceIdServ() {
    return instanceIdServ;
  }

  public void setInstanceIdServ(String package_instance_id_serv) {
    this.instanceIdServ = package_instance_id_serv;
  }

  public int getAccountNumber() {
    return accountNumber;
  }

  public void setAccountNumber(int accountNumber) {
    this.accountNumber = accountNumber;
  }

  public String getName() {
    return name;
  }

  public void setName(String package_name) {
    this.name = package_name;
  }

  public Date getActiveDate() {
    return activeDate;
  }

  public void setActiveDate(Date active_date) {
    this.activeDate = active_date;
  }

  public Date getInactiveDate() {
    return inactiveDate;
  }

  public void setInactiveDate(Date inactive_date) {
    this.inactiveDate = inactive_date;
  }

  public Collection<Component> getComponentList() {
    return componentList;
  }

  public void setComponentList(Collection<Component> componentlist) {
    this.componentList = componentlist;
  }

  @Override
  public String toString() {
    return "Package [id=" + id + ", instanceId=" + instanceId + ", instanceIdServ=" + instanceIdServ + ", name=" + name
        + ", activeDate=" + activeDate + ", inactiveDate=" + inactiveDate + ", componentList=" + componentList + "]";
  }

}