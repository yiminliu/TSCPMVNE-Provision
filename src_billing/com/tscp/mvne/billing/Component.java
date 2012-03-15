package com.tscp.mvne.billing;

import java.util.Date;

// TODO jpong: Change property names to match java convention. This will require hibernate re-mapping. This needs to be done for all ORM objects.
public class Component {
  private int id = 0;
  private int instanceId;
  private String name;
  private int elementId;
  private Date activeDate;
  private Date inactiveDate;

  public Component() {
    // do nothing
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public int getInstanceId() {
    return instanceId;
  }

  public void setInstanceId(int instanceId) {
    this.instanceId = instanceId;
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
    return "Component [component_id=" + id + ", component_instance_id=" + instanceId + ", component_name=" + name
        + ", element_id=" + elementId + ", active_date=" + activeDate + ", inactive_date=" + inactiveDate + "]";
  }

}