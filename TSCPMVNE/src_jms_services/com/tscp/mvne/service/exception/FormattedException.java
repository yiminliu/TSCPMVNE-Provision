package com.tscp.mvne.service.exception;

import java.text.MessageFormat;

public class FormattedException extends Exception {
  private static final long serialVersionUID = -7363797718611904280L;

  public FormattedException() {
    super();
  }

  public FormattedException(String message) {
    super(message);
  }

  public FormattedException(String pattern, Object... args) {
    super(formatMessage(pattern, args));
  }

  public FormattedException(String message, Throwable cause) {
    super(message, cause);
  }

  public FormattedException(Throwable cause, String pattern, Object... args) {
    super(formatMessage(pattern, args), cause);
  }

  protected static String formatMessage(String pattern, Object... args) {
    return MessageFormat.format(pattern, args);
  }

}
