package com.tscp.mvne.util.logger;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MethodLogger {
  private static final Logger logger = LoggerFactory.getLogger("TSCPMVNE");

  public static void logMethodExit(String methodName) {
    logger.info("Exiting {}", methodName);
  }

  public static void logMethodReturn(String methodName, Object object) {
    logger.info("{} Returning {}", methodName, object.toString());
  }

  public static void logMethod(String methodName, Object... args) {
    String argString = "";
    for (int i = 0; i < args.length; i++) {
      argString += "ARG" + i + "=" + (args[i] == null ? "NULL" : args[i].toString()) + " ";
    }
    logger.info("Begin {} with args {}", methodName, argString);
  }

  public static void logMethod(String methodName, List<Object> args) {
    String argString = "";
    for (int i = 0; i < args.size(); i++) {
      argString += "ARG" + i + "=" + (args.get(i) == null ? "NULL" : args.get(i).toString()) + " ";
    }
    logger.info("Begin {} with args {}", methodName, argString);
  }

}
