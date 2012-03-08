package com.tscp.mvne.logger;

import org.apache.log4j.Logger;

public class TscpmvneLogger {
  private static final Logger logger = Logger.getLogger("tscpmvneLogger");

  public static final Logger getInstance() {
    return logger;
  }

}
