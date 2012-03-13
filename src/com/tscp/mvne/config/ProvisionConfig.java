package com.tscp.mvne.config;

public final class ProvisionConfig extends Config {
  public static boolean initialized = false;

  public static final class Package {
    public static String id;
    public static String externalIdType;
    public static String instanceServId;
    public static String instanceId;

    public static String print() {
      return "ProvisionConfig.Package [id=" + id + ", externalIdType=" + externalIdType + ", instanceServId=" + instanceServId
          + ", instanceId=" + instanceId + "]";
    }
  }

  public static final class Component {
    public static String packageId;
    public static String installId;
    public static String reinstallId;
    public static String suspendId;
    public static String externalIdType;
    public static String instanceServId;

    public static String print() {
      return "ProvisionConfig.Component [packageId=" + packageId + ", installId=" + installId + ", reinstallId=" + reinstallId
          + ", suspendId=" + suspendId + ", externalIdType=" + externalIdType + ", instanceServId=" + instanceServId + "]";
    }

  }

  public ProvisionConfig() {
    init(PropertiesLocation.configFile);
    if (!initialized) {
      loadValues();
    }
  }

  public static final void init() {
    init(PropertiesLocation.configFile);
    loadValues();
  }

  private static final void loadValues() {
    // Package values
    Package.id = properties.getProperty("package.id");
    Package.externalIdType = properties.getProperty("package.externalId.type");
    Package.instanceServId = properties.getProperty("package.instance.serv.id");
    Package.instanceId = properties.getProperty("package.instance.id");
    // Component values
    Component.packageId = properties.getProperty("component.package.id");
    Component.externalIdType = properties.getProperty("component.externalId.type");
    Component.installId = properties.getProperty("component.id.install");
    Component.reinstallId = properties.getProperty("component.id.reinstall");
    Component.suspendId = properties.getProperty("component.id.suspend");
    Component.instanceServId = properties.getProperty("component.instance.serv.id");
    // Set initialized
    initialized = true;
  }
}
