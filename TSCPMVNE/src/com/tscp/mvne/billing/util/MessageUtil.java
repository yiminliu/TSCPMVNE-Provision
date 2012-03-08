package com.tscp.mvne.billing.util;

import com.tscp.mvne.billing.api.MessageHolder;

public class MessageUtil {

  public static String getStatus(MessageHolder messageHolder) {
    return "Status: " + messageHolder.getStatus() + " Msg: " + messageHolder.getMessage();
  }
}
