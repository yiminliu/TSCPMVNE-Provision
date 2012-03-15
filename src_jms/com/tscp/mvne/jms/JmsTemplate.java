package com.tscp.mvne.jms;

public abstract class JmsTemplate {
  protected String connectionFactory;
  protected String destination;
  protected boolean initialized = false;

  protected final String getConnectionFactory() {
    return connectionFactory;
  }

  protected final void setConnectionFactory(String connectionFactory) {
    this.connectionFactory = connectionFactory;
  }

  protected final String getDestination() {
    return destination;
  }

  protected final void setDestination(String destination) {
    this.destination = destination;
  }

  protected final boolean isInitialized() {
    return initialized;
  }

  protected final void setInitialized(boolean initialized) {
    this.initialized = initialized;
  }

}