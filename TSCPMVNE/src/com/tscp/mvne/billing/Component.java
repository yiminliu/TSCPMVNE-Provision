package com.tscp.mvne.billing;

import java.util.Date;

public class Component {
  private int componentId;
  private int componentInstanceId;
  private String name;
  private int elementId;
  private Date activeDate;
  private Date inactiveDate;

  public int getComponentId() {
    return componentId;
  }

  public void setComponentId(int componentId) {
    this.componentId = componentId;
  }

  public int getComponentInstanceId() {
    return componentInstanceId;
  }

  public void setComponentInstanceId(int componentInstanceId) {
    this.componentInstanceId = componentInstanceId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getElementId() {
    return elementId;
  }

  public void setElementId(int elementId) {
    this.elementId = elementId;
  }

  public Date getActiveDate() {
    return activeDate;
  }

  public void setActiveDate(Date activeDate) {
    this.activeDate = activeDate;
  }

  public Date getInactiveDate() {
    return inactiveDate;
  }

  public void setInactiveDate(Date inactiveDate) {
    this.inactiveDate = inactiveDate;
  }

  @Override
  public String toString() {
    return "Component [componentId=" + componentId + ", componentInstanceId=" + componentInstanceId + ", name=" + name
        + ", elementId=" + elementId + ", activeDate=" + activeDate + ", inactiveDate=" + inactiveDate + "]";
  }

}