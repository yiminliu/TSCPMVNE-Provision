package com.tscp.mvne.jms;

import java.io.Serializable;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.Topic;
import javax.jms.TopicConnection;
import javax.jms.TopicConnectionFactory;
import javax.jms.TopicPublisher;
import javax.jms.TopicSession;
import javax.naming.NamingException;

public abstract class AbstractTopicPublisher extends JmsTemplate {
  TopicConnectionFactory topicConnectionFactory;
  TopicConnection topicConnection;
  TopicSession topicSession;
  Topic topic;
  TopicPublisher topicPublisher;

  public void init() {
    try {
      topicConnectionFactory = (TopicConnectionFactory) JmsHelper.jndiLookup(connectionFactory);
      topicConnection = topicConnectionFactory.createTopicConnection();
      topicSession = topicConnection.createTopicSession(true, Session.AUTO_ACKNOWLEDGE);
      topic = JmsHelper.getTopic(destination, topicSession);
      topicPublisher = topicSession.createPublisher(topic);
      initialized = true;
    } catch (NamingException e) {
      e.printStackTrace();
    } catch (JMSException e) {
      e.printStackTrace();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void publish(Message message) {
    if (!initialized) {
      init();
    }
    try {
      topicPublisher.publish(message);
      // topicPublisher.publish(topicSession.createMessage());
    } catch (JMSException e) {
      e.printStackTrace();
    }
  }

  public void publish(Serializable request) {
    if (!initialized) {
      init();
    }
    try {
      publish(topicSession.createObjectMessage(request));
    } catch (JMSException e) {
      e.printStackTrace();
    }
  }
}