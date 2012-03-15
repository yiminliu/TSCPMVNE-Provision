package com.tscp.mvne.network;

import java.net.NetworkInterface;
import java.util.Iterator;
import java.util.List;

import com.tscp.mvne.config.Device;
import com.tscp.mvne.network.exception.NetworkException;
import com.tscp.mvne.network.service.NetworkServiceProvider;
import com.tscp.mvno.webservices.API3;
import com.tscp.mvno.webservices.AccessEqpAsgmInfo;
import com.tscp.mvno.webservices.ActivateReserveSubscription;
import com.tscp.mvno.webservices.ApiActivateReserveSubscriptionResponseHolder;
import com.tscp.mvno.webservices.ApiGeneralResponseHolder;
import com.tscp.mvno.webservices.ApiPendingSubscriptionNPAResponseHolder;
import com.tscp.mvno.webservices.ApiResellerSubInquiryResponseHolder;
import com.tscp.mvno.webservices.ApiSwapESNResponseHolder;
import com.tscp.mvno.webservices.PendingSubscriptionNPA;
import com.tscp.mvno.webservices.Sali2;

public class NetworkSystem {
  protected static final API3 port = NetworkServiceProvider.getInstance();

  public NetworkSystem() {
    // do nothing
  }

  public void activateMDN(NetworkInfo networkinfo) throws NetworkException {
    if (networkinfo == null) {
      throw new NetworkException("activateMDN", "Network Information must be provided for service activation");
    } else {
      if ((networkinfo.getEsnmeiddec() == null || networkinfo.getEsnmeiddec().trim().length() == 0)
          && (networkinfo.getEsnmeidhex() == null || networkinfo.getEsnmeidhex().trim().length() == 0)) {
        throw new NetworkException("activateMDN", "Please specify an ESN or MEID to activate on...");
      }
      if (networkinfo.getMdn() == null || networkinfo.getMdn().trim().length() == 0) {
        throw new NetworkException("activateMDN", "Please specify an MDN to activate on...");
      }
      if (networkinfo.getMsid() == null || networkinfo.getMsid().trim().length() == 0) {
        throw new NetworkException("activateMDN", "Please specify the MSID associate with MDN " + networkinfo.getMdn() + "...");
      }
    }

    ActivateReserveSubscription activatereservesubscription = new ActivateReserveSubscription();

    // TODO this value isn't used anywhere, should we use this or the value from
    // networkInfo? NetworkInfo may always have both hex and dec populated
    String ESN = "";
    if (networkinfo.getEsnmeiddec() == null || networkinfo.getEsnmeiddec().trim().length() == 0) {
      ESN = networkinfo.getEsnmeidhex();
    } else {
      ESN = networkinfo.getEsnmeiddec();
    }
    activatereservesubscription.setESN(networkinfo.getEsnmeiddec());
    activatereservesubscription.setMDN(networkinfo.getMdn());
    activatereservesubscription.setMSID(networkinfo.getMsid());

    ApiActivateReserveSubscriptionResponseHolder response = port.apIactivatePendingSubscription(activatereservesubscription);
    if (response == null) {
      throw new NetworkException("activateMDN", "No response returned from Network Element...");
    } else {
      if (!response.getStatusMessage().equals("SUCCEED")) {
        NetworkException networkexception = new NetworkException("activateMDN", "Error activating Device "
            + networkinfo.getEsnmeiddec() + " against MDN " + networkinfo.getMdn() + " using MSID " + networkinfo.getMsid()
            + "..." + response.getResponseMessage());
        networkexception.setNetworkinfo(networkinfo);
        throw networkexception;
      }
    }

  }

  public void disconnectService(NetworkInfo networkinfo) throws NetworkException {
    if (networkinfo == null) {
      throw new NetworkException("disconnectService", "Please provide a network element");
    }
    if (networkinfo.getMdn() == null || networkinfo.getMdn().trim().length() != 10) {
      throw new NetworkException("disconnectService", "Please provide a 10 digit MDN to disconnect");
    }
    String expirationdate = null;
    NetworkInfo currentNetworkStatus = getNetworkInfo(null, networkinfo.getMdn());
    if (currentNetworkStatus != null && currentNetworkStatus.getExpirationdate() == null) {
      ApiGeneralResponseHolder response = port.apIexpireSubscription(networkinfo.getMdn(), expirationdate);
      if (response == null) {
        throw new NetworkException("disconnectService", "Error retrieving response information from network element...");
      } else {
        if (!response.getStatusMessage().equals("SUCCEED")) {
          NetworkException networkexception = new NetworkException("disconnectService",
              "Error returned when disconnecting service for MDN " + networkinfo.getMdn() + " :: "
                  + response.getApiResponseMessage() + " :: " + response.getStatusMessage() + " :: "
                  + response.getResponseMessage());
          networkexception.setNetworkinfo(networkinfo);
          throw networkexception;
        }
      }
    } else {
      if (currentNetworkStatus == null) {
        throw new NetworkException("MDN " + networkinfo.getMdn() + " is not found on the network.");
      } else {
        System.out.println("MDN " + networkinfo.getMdn() + " is already in disconnected status...");
      }
    }
  }

  public NetworkInfo getNetworkInfo(String esn, String mdn) throws NetworkException {
    if ((esn == null || esn.length() == 0) && (mdn == null || mdn.length() == 0)) {
      throw new NetworkException("getNetworkInfo", "ESN or an MDN required");
    } else if (esn != null && esn.length() > 0 && mdn != null && mdn.length() > 0) {
      throw new NetworkException("getNetworkInfo", "Only an ESN or an MDN may be used.");
    } else {
      System.out.println("BEGIN => Network informational query for " + (esn == null ? "MDN :: " + mdn : "ESN :: " + esn));
      esn = esn == null ? esn : esn.trim();
      mdn = mdn == null ? mdn : mdn.trim();
      NetworkInfo networkinfo = new NetworkInfo();
      networkinfo.setEsnmeiddec(esn);
      networkinfo.setMdn(mdn);
      ApiResellerSubInquiryResponseHolder subscription = port.apIresellerV2SubInquiry(esn, mdn);
      if (subscription != null) {
        if (subscription.getAccessNbrAsgmList() != null && subscription.getAccessNbrAsgmList().getValue() != null
            && subscription.getAccessNbrAsgmList().getValue().size() >= 1) {
          if (subscription.getAccessNbrAsgmList().getValue().get(0).getSwitchStatusCd().equals("C")) {
            networkinfo.setExpirationdate(subscription.getAccessNbrAsgmList().getValue().get(0).getAccessNbrAsgmEffDt());
            networkinfo.setExpirationtime(subscription.getAccessNbrAsgmList().getValue().get(0).getAccessNbrAsgmEffTm());
          }
          networkinfo.setMdn(subscription.getAccessNbrAsgmList().getValue().get(0).getAccessNbr());
          networkinfo.setMsid(subscription.getAccessNbrAsgmList().getValue().get(0).getMSID());
          networkinfo.setStatus(subscription.getAccessNbrAsgmList().getValue().get(0).getSwitchStatusCd());
        }

        if (subscription.getAccessEqpAsgmList() != null && subscription.getAccessEqpAsgmList().getValue() != null
            && subscription.getAccessEqpAsgmList().getValue().size() >= 1) {
          Iterator<AccessEqpAsgmInfo> iter = subscription.getAccessEqpAsgmList().getValue().iterator();
          if (esn != null) {
            int iteration = 0;
            while (iter.hasNext()) {
              AccessEqpAsgmInfo accessEqp = (AccessEqpAsgmInfo) iter.next();
              String getESN = accessEqp.getESNMEIDDcmlId();
              if ((getESN != null) && (getESN.equals(esn))) {
                if ((iteration > 0) && (accessEqp.getEqpExprDt() != null) && (!accessEqp.getEqpExprDt().trim().isEmpty())) {
                  networkinfo.setStatus("P");
                }
                networkinfo.setEffectivedate(accessEqp.getEqpEffDt());
                networkinfo.setEffectivetime(accessEqp.getEqpEffTm());
                networkinfo.setEsnmeiddec(accessEqp.getESNMEIDDcmlId());
                networkinfo.setEsnmeidhex(accessEqp.getESNMEIDHexId());
                break;
              }
              iteration++;
            }
          } else {
            networkinfo.setEffectivedate(subscription.getAccessEqpAsgmList().getValue().get(0).getEqpEffDt());
            networkinfo.setEffectivetime(subscription.getAccessEqpAsgmList().getValue().get(0).getEqpEffTm());
            networkinfo.setEsnmeiddec(subscription.getAccessEqpAsgmList().getValue().get(0).getESNMEIDDcmlId());
            networkinfo.setEsnmeidhex(subscription.getAccessEqpAsgmList().getValue().get(0).getESNMEIDHexId());
          }
        }
        System.out.println("MDN      :: " + networkinfo.getMdn());
        System.out.println("MSID     :: " + networkinfo.getMsid());
        System.out.println("EffDate  :: " + networkinfo.getEffectivedate());
        System.out.println("EffTime  :: " + networkinfo.getEffectivetime());
        System.out.println("ExpDate  :: " + networkinfo.getExpirationdate());
        System.out.println("ExpTime  :: " + networkinfo.getExpirationtime());
        System.out.println("ESNDec   :: " + networkinfo.getEsnmeiddec());
        System.out.println("ESNHex   :: " + networkinfo.getEsnmeidhex());
        System.out.println("Status   :: " + networkinfo.getStatus());
        System.out.println("DONE => Network informational query for " + (esn == null ? "MDN :: " + mdn : "ESN :: " + esn));
        return networkinfo;
      } else {
        NetworkException networkexception = new NetworkException("getNetworkInfo", "Subscriber not found for "
            + (networkinfo.getEsnmeiddec() == null || networkinfo.getEsnmeiddec().length() == 0 ? " ESN "
                + networkinfo.getEsnmeiddec() : " MDN " + networkinfo.getMdn()));
        networkexception.setNetworkinfo(networkinfo);
        System.out.println("Subscriber not found for "
            + (networkinfo.getEsnmeiddec() == null || networkinfo.getEsnmeiddec().length() == 0 ? " ESN "
                + networkinfo.getEsnmeiddec() : " MDN " + networkinfo.getMdn()));
        System.out.println("DONE => Network informational query for " + (esn == null ? "MDN :: " + mdn : "ESN :: " + esn));
        throw networkexception;
      }
    }
  }

  @Deprecated
  public NetworkInfo getNetworkInfo_old(String esn, String mdn) throws NetworkException {
    if ((esn == null || esn.length() == 0) && (mdn == null || mdn.length() == 0)) {
      throw new NetworkException("getNetworkInfo", "ESN or an MDN required");
    } else if (esn != null && esn.length() > 0 && mdn != null && mdn.length() > 0) {
      throw new NetworkException("getNetworkInfo", "Only an ESN or an MDN may be used.");
    } else {
      System.out.println("BEGIN => Network informational query for " + (esn == null ? "MDN :: " + mdn : "ESN :: " + esn));
      NetworkInfo networkinfo = new NetworkInfo();
      networkinfo.setEsnmeiddec(esn.trim());
      networkinfo.setMdn(mdn.trim());
      ApiResellerSubInquiryResponseHolder subscription = port.apIresellerV2SubInquiry(esn.trim(), mdn.trim());
      if (subscription != null) {
        if (subscription.getAccessNbrAsgmList() != null && subscription.getAccessNbrAsgmList().getValue() != null
            && subscription.getAccessNbrAsgmList().getValue().size() >= 1) {
          if (subscription.getAccessNbrAsgmList().getValue().get(0).getSwitchStatusCd().equals("C")) {
            networkinfo.setExpirationdate(subscription.getAccessNbrAsgmList().getValue().get(0).getAccessNbrAsgmEffDt());
            networkinfo.setExpirationtime(subscription.getAccessNbrAsgmList().getValue().get(0).getAccessNbrAsgmEffTm());
          }
          networkinfo.setMdn(subscription.getAccessNbrAsgmList().getValue().get(0).getAccessNbr());
          networkinfo.setMsid(subscription.getAccessNbrAsgmList().getValue().get(0).getMSID());
          networkinfo.setStatus(subscription.getAccessNbrAsgmList().getValue().get(0).getSwitchStatusCd());
        }

        if (subscription.getAccessEqpAsgmList() != null && subscription.getAccessEqpAsgmList().getValue() != null
            && subscription.getAccessEqpAsgmList().getValue().size() >= 1) {
          networkinfo.setEffectivedate(subscription.getAccessEqpAsgmList().getValue().get(0).getEqpEffDt());
          networkinfo.setEffectivetime(subscription.getAccessEqpAsgmList().getValue().get(0).getEqpEffTm());
          networkinfo.setEsnmeiddec(subscription.getAccessEqpAsgmList().getValue().get(0).getESNMEIDDcmlId());
          networkinfo.setEsnmeidhex(subscription.getAccessEqpAsgmList().getValue().get(0).getESNMEIDHexId());
        }
        System.out.println("MDN      :: " + networkinfo.getMdn());
        System.out.println("MSID     :: " + networkinfo.getMsid());
        System.out.println("EffDate  :: " + networkinfo.getEffectivedate());
        System.out.println("EffTime  :: " + networkinfo.getEffectivetime());
        System.out.println("ExpDate  :: " + networkinfo.getExpirationdate());
        System.out.println("ExpTime  :: " + networkinfo.getExpirationtime());
        System.out.println("ESNDec   :: " + networkinfo.getEsnmeiddec());
        System.out.println("ESNHex   :: " + networkinfo.getEsnmeidhex());
        System.out.println("Status   :: " + networkinfo.getStatus());
        System.out.println("DONE => Network informational query for " + (esn == null ? "MDN :: " + mdn : "ESN :: " + esn));
        return networkinfo;
      } else {
        NetworkException networkexception = new NetworkException("getNetworkInfo", "Subscriber not found for "
            + (networkinfo.getEsnmeiddec() == null || networkinfo.getEsnmeiddec().length() == 0 ? " ESN "
                + networkinfo.getEsnmeiddec() : " MDN " + networkinfo.getMdn()));
        networkexception.setNetworkinfo(networkinfo);
        System.out.println("Subscriber not found for "
            + (networkinfo.getEsnmeiddec() == null || networkinfo.getEsnmeiddec().length() == 0 ? " ESN "
                + networkinfo.getEsnmeiddec() : " MDN " + networkinfo.getMdn()));
        System.out.println("DONE => Network informational query for " + (esn == null ? "MDN :: " + mdn : "ESN :: " + esn));
        throw networkexception;
      }
    }
  }

  public NetworkInfo getSwapNetworkInfo(String esn, String mdn) throws NetworkException {
    if ((esn == null || esn.length() == 0)) {
      throw new NetworkException("getSwapNetworkInfo", "ESN required");
    } else {
      System.out.println("BEGIN => Network informational query for swapping " + "ESN :: " + esn);
      esn = esn.trim();
      NetworkInfo networkinfo = new NetworkInfo();
      switch (esn.length()) {
      case Device.ESN_HEX:
      case Device.MEID_HEX:
        networkinfo.setEsnmeidhex(esn);
        break;
      case Device.ESN_DEC:
      case Device.MEID_DEC:
        networkinfo.setEsnmeiddec(esn);
        break;
      default:
        throw new NetworkException("Invalid ESN length");
      }

      ApiResellerSubInquiryResponseHolder subscription = port.apIresellerV2SubInquiry(esn, null);
      if (subscription != null) {
        if (subscription.getAccessNbrAsgmList() != null && subscription.getAccessNbrAsgmList().getValue() != null
            && subscription.getAccessNbrAsgmList().getValue().size() >= 1) {
          if (subscription.getAccessNbrAsgmList().getValue().get(0).getSwitchStatusCd().equals("C")) {
            networkinfo.setExpirationdate(subscription.getAccessNbrAsgmList().getValue().get(0).getAccessNbrAsgmEffDt());
            networkinfo.setExpirationtime(subscription.getAccessNbrAsgmList().getValue().get(0).getAccessNbrAsgmEffTm());
          }
          networkinfo.setMdn(subscription.getAccessNbrAsgmList().getValue().get(0).getAccessNbr());
          networkinfo.setMsid(subscription.getAccessNbrAsgmList().getValue().get(0).getMSID());
          networkinfo.setStatus(subscription.getAccessNbrAsgmList().getValue().get(0).getSwitchStatusCd());
        }

        if (subscription.getAccessEqpAsgmList() != null && subscription.getAccessEqpAsgmList().getValue() != null
            && subscription.getAccessEqpAsgmList().getValue().size() >= 1) {
          Iterator<AccessEqpAsgmInfo> iter = subscription.getAccessEqpAsgmList().getValue().iterator();
          int iteration = 0;
          while (iter.hasNext()) {
            AccessEqpAsgmInfo accessEqp = (AccessEqpAsgmInfo) iter.next();
            String getESN = accessEqp.getESNMEIDDcmlId();
            if ((getESN != null) && (getESN.equals(esn))) {
              if ((iteration > 0) && (accessEqp.getEqpExprDt() != null) && (!accessEqp.getEqpExprDt().trim().isEmpty())) {
                networkinfo.setStatus("P");
              }
              networkinfo.setEffectivedate(accessEqp.getEqpEffDt());
              networkinfo.setEffectivetime(accessEqp.getEqpEffTm());
              networkinfo.setEsnmeiddec(accessEqp.getESNMEIDDcmlId());
              networkinfo.setEsnmeidhex(accessEqp.getESNMEIDHexId());
              break;
            }
            iteration++;
          }
        }
        System.out.println("MDN      :: " + networkinfo.getMdn());
        System.out.println("MSID     :: " + networkinfo.getMsid());
        System.out.println("EffDate  :: " + networkinfo.getEffectivedate());
        System.out.println("EffTime  :: " + networkinfo.getEffectivetime());
        System.out.println("ExpDate  :: " + networkinfo.getExpirationdate());
        System.out.println("ExpTime  :: " + networkinfo.getExpirationtime());
        System.out.println("ESNDec   :: " + networkinfo.getEsnmeiddec());
        System.out.println("ESNHex   :: " + networkinfo.getEsnmeidhex());
        System.out.println("Status   :: " + networkinfo.getStatus());
        System.out.println("DONE => Network informational query for swapping ESN :: " + esn);
        return networkinfo;
      } else {
        NetworkException networkexception = new NetworkException("getSwapNetworkInfo", "Subscriber not found for " + " ESN "
            + esn);
        networkexception.setNetworkinfo(networkinfo);
        System.out.println("Subscriber not found for ESN " + esn);
        System.out.println("DONE => Network informational query for swapping ESN :: " + esn);
        throw networkexception;
      }
    }
  }

  public NetworkInfo reserveMDN(String csa, String priceplan, List<String> soclist) throws NetworkException {
    PendingSubscriptionNPA pendingsubscription = new PendingSubscriptionNPA();

    Sali2 sali2 = new Sali2();
    if (priceplan == null) {
      sali2.setSvcCode("PRSCARD5");
    } else {
      sali2.setSvcCode(priceplan);
    }
    pendingsubscription.setPricePlans(sali2);

    if (csa == null) {
      pendingsubscription.setCSA("LAXLAX213");
    } else {
      pendingsubscription.setCSA(csa);
    }

    ApiPendingSubscriptionNPAResponseHolder subscription = port.apIreserveSubscriptionNPA(pendingsubscription);
    if (subscription != null) {
      try {
        NetworkInfo networkinfo = new NetworkInfo();
        networkinfo.setMdn(subscription.getSubNPA().getMDN());
        networkinfo.setMsid(subscription.getSubNPA().getMSID());
        return networkinfo;
      } catch (NullPointerException npe) {
        throw new NetworkException("reserveMDN", "required object "
            + (subscription.getSubNPA() == null ? subscription.getSubNPA().getMDN() == null ? subscription.getSubNPA()
                .getMSID() == null ? npe.getMessage() : " MSID " : " MDN " : " SubNPA ") + " is null...");
      }
    } else {
      throw new NetworkException("reserveMDN", "returned subscription is empty...");
    }
  }

  public void restoreService(NetworkInfo networkinfo) throws NetworkException {
    if (networkinfo == null) {
      throw new NetworkException("restoreService", "Please provide a network element to be restored...");
    } else {
      if (networkinfo.getMdn() == null || networkinfo.getMdn().trim().length() == 0) {
        throw new NetworkException("restoreService", "Please provide an MDN to be restored...");
      }
    }

    ApiGeneralResponseHolder response = port.apIrestoreSubscription(networkinfo.getMdn());
    if (response == null) {
      throw new NetworkException("suspendService", "No response returned from Network Interface...");
    } else {
      if (!response.getStatusMessage().equals("SUCCEED")) {
        NetworkException networkexception = new NetworkException("restoreService", "Failure to restore " + networkinfo.getMdn()
            + "...Message returned from Network Interface was " + response.getResponseMessage());
        networkexception.setNetworkinfo(networkinfo);
        throw networkexception;
      }
    }

  }

  public void suspendService(NetworkInfo networkinfo) throws NetworkException {
    if (networkinfo == null) {
      throw new NetworkException("suspendService", "Please provide a network element to be suspended...");
    } else {
      if (networkinfo.getMdn() == null || networkinfo.getMdn().trim().length() == 0) {
        throw new NetworkException("suspendService", "Please provide an MDN to be suspended...");
      }
    }
    /**
     * this value can be HTL to hotline but we're just going for full suspend.
     */
    String suspendcode = null;
    ApiGeneralResponseHolder response = port.apIsuspendSubscription(networkinfo.getMdn(), suspendcode);
    if (response == null) {
      throw new NetworkException("suspendService", "No response returned from Network Interface...");
    } else {
      if (!response.getStatusMessage().equals("SUCCEED")) {
        NetworkException networkexception = new NetworkException("suspendService", "Failure to suspend " + networkinfo.getMdn()
            + "...Message returned from Network Interface was " + response.getResponseMessage());
        networkexception.setNetworkinfo(networkinfo);
        throw networkexception;
      }
    }

  }

  public void swapESN(NetworkInfo oldNetworkInfo, NetworkInfo newNetworkInfo) throws NetworkException {
    if (oldNetworkInfo == null || ((oldNetworkInfo.getMdn() == null || oldNetworkInfo.getMdn().trim().isEmpty()))) {
      throw new NetworkException("Old network information MDN must be provided");
    }
    if (newNetworkInfo == null
        || ((newNetworkInfo.getEsnmeiddec() == null || newNetworkInfo.getEsnmeiddec().trim().isEmpty()) && (newNetworkInfo
            .getEsnmeidhex() == null || newNetworkInfo.getEsnmeidhex().trim().isEmpty()))) {
      throw new NetworkException("New network information must be provided");
    }
    String newEsn = "";
    if (newNetworkInfo.getEsnmeiddec() != null) {
      newEsn = newNetworkInfo.getEsnmeiddec();
      if (newEsn.length() != Device.ESN_DEC && newEsn.length() != Device.MEID_DEC) {
        throw new NetworkException("Dec ESN is not of a valid length");
      }
    }
    if (newNetworkInfo.getEsnmeidhex() != null) {
      newEsn = newNetworkInfo.getEsnmeidhex();
      if (newEsn.length() != Device.ESN_HEX && newEsn.length() != Device.MEID_HEX) {
        throw new NetworkException("Hex ESN is not of a valid length");
      }
    }

    if (oldNetworkInfo.getStatus().equals("A")) {
      ApiSwapESNResponseHolder responseHolder = port.apIswapESN(oldNetworkInfo.getMdn(), newEsn);

      if (responseHolder != null) {
        if (responseHolder.getStatusMessage().equals("SUCCEED")) {
          if (responseHolder.getMSID().equals(oldNetworkInfo.getMsid())) {
            newNetworkInfo.setMdn(oldNetworkInfo.getMdn());
            newNetworkInfo.setMsid(responseHolder.getMSID());
          } else {
            responseHolder.setStatusMessage("FAIL");
            throw new NetworkException("Error swapping ESN to " + newEsn + " for MDN " + oldNetworkInfo.getMdn()
                + " MSID did not match.");
          }
        } else {
          responseHolder.setStatusMessage("FAIL");
          throw new NetworkException("Error swapping ESN to " + newEsn + " for MDN " + oldNetworkInfo.getMdn() + "... "
              + responseHolder.getResponseMessage());
        }

        // if (responseHolder != null) {
        // if (!responseHolder.getStatusMessage().equals("SUCCEED")) {
        // throw new NetworkException("Error swapping to Device " + newEsn +
        // " for MDN " + oldNetworkInfo.getMdn()
        // + "... " + responseHolder.getResponseMessage());
        // } else {
        // newNetworkInfo.setMdn(oldNetworkInfo.getMdn());
        // newNetworkInfo.setMsid(responseHolder.getMSID());
        // }

      } else {
        throw new NetworkException("No response from gateway...");
      }
    } else if (oldNetworkInfo.getStatus().equals("C")) {
      newNetworkInfo.setMdn(oldNetworkInfo.getMdn());
      newNetworkInfo.setMsid(oldNetworkInfo.getMsid());
    } else {
      throw new NetworkException("No swap was performed.");
    }
  }

}