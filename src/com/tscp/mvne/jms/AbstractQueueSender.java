package com.tscp.mvne.jms;

import java.io.Serializable;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.naming.NamingException;

public abstract class AbstractQueueSender extends JmsTemplate {
  QueueConnectionFactory queueConnectionFactory;
  QueueConnection queueConnection;
  QueueSession queueSession;
  Queue queue;
  QueueSender queueSender;

  public void init() {
    try {
      queueConnectionFactory = (QueueConnectionFactory) JmsHelper.jndiLookup(connectionFactory);
      queueConnection = queueConnectionFactory.createQueueConnection();
      queueSession = queueConnection.createQueueSession(true, Session.AUTO_ACKNOWLEDGE);
      queue = JmsHelper.getQueue(destination, queueSession);
      queueSender = queueSession.createSender(queue);
      initialized = true;
    } catch (NamingException e) {
      e.printStackTrace();
    } catch (JMSException e) {
      e.printStackTrace();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void send(Message message) {
    if (!initialized) {
      init();
    }
    try {
      queueSender.send(message);
      // queueSender.send(queueSession.createMessage());
    } catch (JMSException e) {
      e.printStackTrace();
    }
  }

  public void send(Serializable request) {
    if (!initialized) {
      init();
    }
    try {
      send(queueSession.createObjectMessage(request));
    } catch (JMSException e) {
      e.printStackTrace();
    }
  }

}
