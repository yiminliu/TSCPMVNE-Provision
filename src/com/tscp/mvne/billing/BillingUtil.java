package com.tscp.mvne.billing;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.hibernate.Query;
import org.hibernate.classic.Session;

import com.telscape.billingserviceinterface.BillName;
import com.telscape.billingserviceinterface.BillingAccount;
import com.telscape.billingserviceinterface.ContactInfo;
import com.telscape.billingserviceinterface.CustAddress;
import com.tscp.mvne.billing.exception.BillingException;
import com.tscp.mvne.billing.exception.ProvisionException;
import com.tscp.mvne.billing.usage.UsageDetail;
import com.tscp.mvne.config.BILLING;
import com.tscp.mvne.config.PROVISION;
import com.tscp.mvne.hibernate.HibernateUtil;

public class BillingUtil extends BillingServerUtil {

  public static final boolean checkChargeMRC(int accountNo, String externalId) throws BillingException {
    Date lastActiveDate = getLastActiveDate(accountNo, externalId);
    return lastActiveDate.getTime() <= (new Date()).getTime();
  }

  public static List<UsageDetail> getChargeHistory(int accountNo, String externalId) {
    Session session = HibernateUtil.getSessionFactory().getCurrentSession();
    session.beginTransaction();
    Query q = session.getNamedQuery("sp_fetch_charge_history");
    q.setParameter("in_account_no", accountNo);
    q.setParameter("in_external_id", externalId);
    List<UsageDetail> usageDetailList = q.list();
    session.getTransaction().rollback();
    return usageDetailList;
  }

  public static final Date getLastActiveDate(int accountNo, String externalId) throws BillingException {
    List<UsageDetail> usageDetailList = getChargeHistory(accountNo, externalId);
    if (usageDetailList != null && !usageDetailList.isEmpty()) {
      for (UsageDetail usageDetail : usageDetailList) {
        if (usageDetail.getUsageType().equals("Access Fee")) {
          return usageDetail.getEndTime();
        }
      }
    } else {
      throw new BillingException("No charge history was found for account " + accountNo + " with MDN " + externalId);
    }
    throw new BillingException("No last access date was found for account " + accountNo + " with MDN " + externalId);
  }

  public static final void checkAccountNumber(int accountNumber) throws BillingException {
    if (accountNumber == 0) {
      throw new ProvisionException("Account Number is not set");
    }
  }

  public static final CustAddress getDefaultBillingCustAddress() {
    CustAddress custAddress = new CustAddress();
    custAddress.setAddress1("");
    custAddress.setAddress2("");
    custAddress.setAddress3("");
    custAddress.setCity("");
    custAddress.setState("");
    custAddress.setZip("");
    custAddress.setCountryCode(PROVISION.SERVICE.COUNTRY.shortValue());
    custAddress.setFranchiseTaxCode(PROVISION.SERVICE.SERVICE_FRANCHISE_TAX.shortValue());
    custAddress.setCounty("");
    return custAddress;
  }

  public static final BillName getDefaultBillingBillName() {
    BillName billName = new BillName();
    billName.setFirstName("");
    billName.setMiddleName("");
    billName.setLastName("");
    return billName;
  }

  public static final XMLGregorianCalendar getCalendar() {
    return getCalendar(new Date());
  }

  public static final XMLGregorianCalendar getCalendar(Date date) {
    try {
      GregorianCalendar cal = new GregorianCalendar();
      cal.setTime(date);
      XMLGregorianCalendar xmlDate = DatatypeFactory.newInstance().newXMLGregorianCalendar(cal);
      return xmlDate;
    } catch (DatatypeConfigurationException e) {
      e.printStackTrace();
      return null;
    }
  }

  public static final BillingAccount getDefaultBillingAccount() {
    BillingAccount billingAccount = new BillingAccount();

    billingAccount.setAccountCategory(BILLING.accountCategory.shortValue());
    billingAccount.setBillDispMethod(BILLING.billDisplayMethod.shortValue());
    billingAccount.setBillFormatOpt(BILLING.billFormatOption);

    BillName billname = new BillName();
    billname.setFirstName("Shell Account");
    billname.setMiddleName("");
    billname.setLastName("TruConnect");
    billingAccount.setBillName(billname);

    billingAccount.setBillPeriod(BILLING.billPeriod.toString());
    billingAccount.setCCardIdServ(BILLING.defaultCreditCardIdServ.shortValue());
    billingAccount.setCollectionIndicator(BILLING.collectionIndicator.shortValue());

    ContactInfo contactinfo = new ContactInfo();
    contactinfo.setContact1Name("");
    contactinfo.setContact1Phone("");
    billingAccount.setContactInfo(contactinfo);

    billingAccount.setCreditThresh(BILLING.creditThreshold.toString());
    billingAccount.setCredStatus(BILLING.creditStatus.shortValue());
    billingAccount.setCurrencyCode(BILLING.currencyCode.shortValue());

    CustAddress custAddress = new CustAddress();
    custAddress.setAddress1("355 S Grand Ave");
    custAddress.setAddress2("");
    custAddress.setAddress3("");
    custAddress.setCity("Los Angeles");
    custAddress.setCountryCode(BILLING.customerCountryCode.shortValue());
    custAddress.setCounty("Los Angeles");
    custAddress.setFranchiseTaxCode(BILLING.customerFranchiseTaxCode.shortValue());
    custAddress.setState("CA");
    custAddress.setZip("90071");
    billingAccount.setCustAddress(custAddress);

    billingAccount.setBillAddress(custAddress);

    billingAccount.setCustEmail("tscwebgeek@telscape.net");
    billingAccount.setCustFaxNo("");
    billingAccount.setCustPhone1("2133880022");
    billingAccount.setCustPhone2("");

    billingAccount.setExrateClass(BILLING.exrateClass.shortValue());
    billingAccount.setExternalAccountNoType(BILLING.accountType.shortValue());
    billingAccount.setInsertGrpId(BILLING.insertGroupId.shortValue());

    billingAccount.setLanguageCode(BILLING.languageCode.shortValue());
    billingAccount.setMarketCode(BILLING.marketCode.shortValue());
    billingAccount.setMsgGroupId(BILLING.messageGroupId.shortValue());

    billingAccount.setOwningCostCtr(BILLING.owningCostCenter.shortValue());
    billingAccount.setPaymentMethod(BILLING.paymentMethod.shortValue());

    billingAccount.setRateClassDefault(BILLING.rateClassDefault.shortValue());

    // TODO Double check this field
    billingAccount.setServiceCenterId(BILLING.serviceCenterId.shortValue());
    billingAccount.setServiceCenterType(BILLING.serviceCenterType.shortValue());

    billingAccount.setSicCode(BILLING.sicCode.shortValue());
    billingAccount.setTieCode(BILLING.tieCode.shortValue());

    billingAccount.setVipCode(BILLING.vipCode.shortValue());

    billingAccount.setSysDate(getCalendar());
    billingAccount.setAccountDateActive(getCalendar());

    return billingAccount;

  }
}
