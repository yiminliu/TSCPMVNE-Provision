package com.tscp.mvne.billing;

import com.telscape.billingserviceinterface.ArrayOfMessageHolder;
import com.telscape.billingserviceinterface.ArrayOfValueHolder;
import com.telscape.billingserviceinterface.MessageHolder;
import com.telscape.billingserviceinterface.ValueHolder;
import com.tscp.mvne.billing.exception.BillingServerException;

public class BillingServerUtil {

  public static ValueHolder checkResponse(ArrayOfValueHolder values) throws BillingServerException {
    if (values == null || values.getValueHolder() == null || values.getValueHolder().isEmpty()) {
      throw new BillingServerException("No response received from billing server");
    } else {
      String serverMessage = "";
      for (ValueHolder value : values.getValueHolder()) {
        if (value.getStatusMessage().getStatus().trim().equalsIgnoreCase("SUCCESS")) {
          return value;
        } else {
          serverMessage = value.getStatusMessage().getMessage() == null ? "" : value.getStatusMessage().getMessage();
        }
      }
      throw new BillingServerException("Action failed on billing server." + serverMessage);
    }
  }

  public static void checkResponse(MessageHolder message) throws BillingServerException {
    if (message == null || message.getStatus() == null) {
      throw new BillingServerException("No response received from billing server");
    } else if (!message.getStatus().toUpperCase().equals("SUCCESS")) {
      throw new BillingServerException("Action failed on billing server. " + message.getMessage());
    }
  }

  public static void checkResponse(ArrayOfMessageHolder messages) throws BillingServerException {
    if (messages == null || messages.getMessageHolder() == null || messages.getMessageHolder().isEmpty()) {
      throw new BillingServerException("No response received from billing server");
    } else {
      String serverMessage = "";
      for (MessageHolder message : messages.getMessageHolder()) {
        if (message.getStatus().toUpperCase().equals("SUCCESS")) {
          return;
        } else {
          serverMessage = message.getMessage() == null ? "" : message.getMessage();
        }
      }
      throw new BillingServerException("Action failed on billing server." + serverMessage);
    }
  }
}
