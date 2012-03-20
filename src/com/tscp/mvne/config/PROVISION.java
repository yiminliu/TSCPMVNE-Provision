package com.tscp.mvne.config;

import com.tscp.mvne.exception.InitializationException;

public final class PROVISION extends Config {

  public static final class SERVICE {
    public static Integer HOTLINE;
    public static Integer RESTORE;
    public static Integer FAILED_PMT;
    public static Integer CURRENCY;
    public static Integer EMF_CONFIG;
    public static Integer EXRATE_CLASS;
    public static Integer EXTERNAL_ACCOUNT_TYPE;
    public static Integer EXTERNAL_ID_TYPE;
    public static Integer RATECLASS;
    public static Integer SALES_CHANNEL;
    public static Integer COUNTRY;
    public static Integer SERVICE_FRANCHISE_TAX;
  }

  public static final class PACKAGE {
    public static Integer ID;
    public static Integer EXTERNAL_ID_TYPE;
    public static Integer INSTANCE_SERV_ID;
    public static Integer INSTANCE_ID;
  }

  public static final class COMPONENT {
    public static Integer PACKAGE_ID;
    public static Integer INSTALL;
    public static Integer REINSTALL;
    public static Integer SUSPEND;
    public static Integer EXTERNAL_ID_TYPE;
    public static Integer INSTANCE_SERV_ID;
  }

  public static final void init() throws InitializationException {
    Config.loadAll();
    try {
      PACKAGE.ID = Integer.parseInt(props.getProperty("package.id"));
      PACKAGE.EXTERNAL_ID_TYPE = Integer.parseInt(props.getProperty("package.externalId.type"));
      PACKAGE.INSTANCE_SERV_ID = Integer.parseInt(props.getProperty("package.instance.serv.id"));
      PACKAGE.INSTANCE_ID = Integer.parseInt(props.getProperty("package.instance.id"));
      COMPONENT.PACKAGE_ID = Integer.parseInt(props.getProperty("component.package.id"));
      COMPONENT.EXTERNAL_ID_TYPE = Integer.parseInt(props.getProperty("component.externalId.type"));
      COMPONENT.INSTALL = Integer.parseInt(props.getProperty("component.id.install"));
      COMPONENT.REINSTALL = Integer.parseInt(props.getProperty("component.id.reinstall"));
      COMPONENT.SUSPEND = Integer.parseInt(props.getProperty("component.id.suspend"));
      COMPONENT.INSTANCE_SERV_ID = Integer.parseInt(props.getProperty("component.instance.serv.id"));
      SERVICE.HOTLINE = Integer.parseInt(props.getProperty("service.threshold.hotline"));
      SERVICE.RESTORE = Integer.parseInt(props.getProperty("service.threshold.restore"));
      SERVICE.FAILED_PMT = Integer.parseInt(props.getProperty("service.threshold.failed_pmt"));
      SERVICE.CURRENCY = Integer.parseInt(props.getProperty("service.currency"));
      SERVICE.EMF_CONFIG = Integer.parseInt(props.getProperty("service.emf_config"));
      SERVICE.EXRATE_CLASS = Integer.parseInt(props.getProperty("service.exrate_class"));
      SERVICE.EXTERNAL_ACCOUNT_TYPE = Integer.parseInt(props.getProperty("service.external_account_type"));
      SERVICE.EXTERNAL_ID_TYPE = Integer.parseInt(props.getProperty("service.external_id_type"));
      SERVICE.RATECLASS = Integer.parseInt(props.getProperty("service.rate_class"));
      SERVICE.SALES_CHANNEL = Integer.parseInt(props.getProperty("service.sales_channel"));
      SERVICE.COUNTRY = Integer.parseInt(props.getProperty("service.country"));
      SERVICE.SERVICE_FRANCHISE_TAX = Integer.parseInt(props.getProperty("service.franchise_tax_code"));
    } catch (NumberFormatException e) {
      e.printStackTrace();
      throw new InitializationException(e);
    }

  }
}