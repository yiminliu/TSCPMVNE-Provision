package com.tscp.mvne.config;

import com.tscp.mvne.exception.InitializationException;

public class BILLING extends Config {
  public static Integer accountCategory;

  public static Integer tieCode;
  public static Integer billSequenceNum;
  public static Integer currencyCode;
  public static Integer languageCode;
  public static Integer accountType;

  public static Integer customerCountryCode;
  public static Integer customerFranchiseTaxCode;
  public static Integer billFranchiseTaxCode;

  public static Integer defaultCreditCardId;
  public static Integer defaultCreditCardIdServ;

  public static Integer creditThreshold;
  public static Integer creditStatus;
  public static Integer creditRating;

  public static Integer paymentMethod;
  public static String billPeriod;
  public static Integer billFrequency;
  public static Integer billFormatOption;
  public static Integer billDisplayMethod;

  public static Integer messageGroupId;
  public static Integer insertGroupId;
  public static Integer rateClassDefault;

  public static Integer exrateClass;
  public static Integer accountStatus;

  public static Integer marketCode;
  public static Integer sicCode;

  public static Integer noBill;
  public static Integer collectionIndicator;
  public static Integer collectionStatus;
  public static Integer collectionHistory;
  public static Integer vipCode;

  public static Integer revRcvCostCtr;
  public static Integer owningCostCenter;
  public static Integer accountSegId;
  public static Integer converted;

  public static Integer chargeThreshold;
  public static Integer threshold;
  public static Integer cyclicalThreshold;

  public static Integer regulatoryId;
  public static Integer globalContractStatus;

  public static Integer serviceCenterId;
  public static Integer serviceCenterType;

  public static Integer paymentTransType;

  public static final void init() throws InitializationException {
    Config.loadAll();
    try {
      accountCategory = Integer.parseInt(props.getProperty("account.account_category"));
      tieCode = Integer.parseInt(props.getProperty("account.tie_code"));
      billSequenceNum = Integer.parseInt(props.getProperty("account.bill_sequence_num"));
      currencyCode = Integer.parseInt(props.getProperty("account.currency_code"));
      languageCode = Integer.parseInt(props.getProperty("account.language_code"));
      accountType = Integer.parseInt(props.getProperty("account.account_type"));
      customerCountryCode = Integer.parseInt(props.getProperty("account.cust_country_code"));
      customerFranchiseTaxCode = Integer.parseInt(props.getProperty("account.cust_franchise_tax_code"));
      billFranchiseTaxCode = Integer.parseInt(props.getProperty("account.bill_franchise_tax_code"));
      defaultCreditCardId = Integer.parseInt(props.getProperty("account.default_ccard_id"));
      defaultCreditCardIdServ = Integer.parseInt(props.getProperty("account.default_ccard_id_serv"));
      creditThreshold = Integer.parseInt(props.getProperty("account.credit_thresh"));
      creditStatus = Integer.parseInt(props.getProperty("account.cred_status"));
      creditRating = Integer.parseInt(props.getProperty("account.credit_rating"));
      paymentMethod = Integer.parseInt(props.getProperty("account.pay_method"));
      billPeriod = props.getProperty("account.bill_period");
      billFrequency = Integer.parseInt(props.getProperty("account.billing_frequency"));
      billFormatOption = Integer.parseInt(props.getProperty("account.bill_fmt_opt"));
      billDisplayMethod = Integer.parseInt(props.getProperty("account.bill_disp_meth"));
      messageGroupId = Integer.parseInt(props.getProperty("account.msg_grp_id"));
      insertGroupId = Integer.parseInt(props.getProperty("account.insert_grp_id"));
      rateClassDefault = Integer.parseInt(props.getProperty("account.rate_class_default"));
      exrateClass = Integer.parseInt(props.getProperty("account.exrate_class"));
      accountStatus = Integer.parseInt(props.getProperty("account.account_status"));
      marketCode = Integer.parseInt(props.getProperty("account.mkt_code"));
      sicCode = Integer.parseInt(props.getProperty("account.sic_code"));
      noBill = Integer.parseInt(props.getProperty("account.no_bill"));
      collectionIndicator = Integer.parseInt(props.getProperty("account.collection_indicator"));
      collectionStatus = Integer.parseInt(props.getProperty("account.collection_status"));
      collectionHistory = Integer.parseInt(props.getProperty("account.collection_history"));
      vipCode = Integer.parseInt(props.getProperty("account.vip_code"));
      revRcvCostCtr = Integer.parseInt(props.getProperty("account.rev_rcv_cost_ctr"));
      owningCostCenter = Integer.parseInt(props.getProperty("account.owning_cost_ctr"));
      accountSegId = Integer.parseInt(props.getProperty("account.acct_seg_id"));
      converted = Integer.parseInt(props.getProperty("account.converted"));
      chargeThreshold = Integer.parseInt(props.getProperty("account.charge_threshold"));
      threshold = Integer.parseInt(props.getProperty("account.threshold"));
      cyclicalThreshold = Integer.parseInt(props.getProperty("account.cyclical_threshold"));
      regulatoryId = Integer.parseInt(props.getProperty("account.regulatory_id"));
      globalContractStatus = Integer.parseInt(props.getProperty("account.global_contract_status"));
      serviceCenterId = Integer.parseInt(props.getProperty("account.svc_ctr_id"));
      serviceCenterType = Integer.parseInt(props.getProperty("account.svc_ctr_type"));
      paymentTransType = Integer.parseInt(props.getProperty("payment.trans_type"));
    } catch (Exception e) {
      e.printStackTrace();
      throw new InitializationException(e);
    }
  }

}
