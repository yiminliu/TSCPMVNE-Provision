package com.tscp.mvne.notification;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tscp.mvne.jms.AbstractQueueSender;

public class NotificationSender extends AbstractQueueSender {

  public static final String QUEUE_USERNAME = "guest";
  public static final String QUEUE_PASSWORD = "guest";
  public static final String QUEUECONFAC = "queue/TestJMS";
  public static final String TOPICCONFAC = "TopicConnectionFactory";

  private static final Logger logger = LoggerFactory.getLogger("TSCPMVNE");

  public NotificationSender() {
    setConnectionFactory(QUEUECONFAC);
    setDestination("jms/Destination");
  }

  // public NotificationSender() {
  // try {
  // queueConnectionFactory = JmsHelper.getQueueConnectionFactory(QUEUECONFAC);
  // queueConnection =
  // queueConnectionFactory.createQueueConnection(JmsHelper.QUEUE_USERNAME,
  // JmsHelper.QUEUE_PASSWORD);
  // queueSession = queueConnection.createQueueSession(true,
  // Session.AUTO_ACKNOWLEDGE);
  // notificationQueue = JmsHelper.getQueue("jms/Destination", queueSession);
  // } catch (Exception e) {
  // e.printStackTrace();
  // if (queueConnection != null) {
  // try {
  // queueConnection.close();
  // } catch (JMSException jms_e) {
  // jms_e.printStackTrace();
  // }
  // }
  // }
  // }

  // @Deprecated
  // public void send(EmailNotification emailNotification) {
  // ObjectMessage objectMessage = null;
  // try {
  // queueSender = queueSession.createSender(notificationQueue);
  // objectMessage = queueSession.createObjectMessage(emailNotification);
  // queueSender.send(objectMessage);
  // queueSender.send(queueSession.createMessage());
  // } catch (JMSException jms_ex) {
  // System.err.println("NotificationSender: Exception " + "occurred: " +
  // jms_ex.toString());
  // jms_ex.printStackTrace();
  // } finally {
  // if (queueConnection != null) {
  // try {
  // queueConnection.close();
  // } catch (JMSException e) {
  // }
  // }
  // }
  // // super.run();
  // }

}