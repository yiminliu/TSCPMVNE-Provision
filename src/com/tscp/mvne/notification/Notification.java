package com.tscp.mvne.notification;

import java.util.Date;
import java.util.List;

import javax.persistence.Enumerated;

import com.tscp.mvne.notification.dao.NotificationParameter;

public interface Notification {

  public int getAttemptNo();

  public Date getCreateDate();

  @Enumerated
  public NotificationCategory getNotificationCategory();

  public int getNotificationId();

  public List<NotificationParameter> getNotificationParameters();

  @Enumerated
  public NotificationType getNotificationType();

  public Date getSentDate();

  public void loadNotification();

  public void saveNotification();

  public void setAttemptNo(int attemptNo);

  public void setCreateDate(Date createDate);

  public void setNotificationCategory(NotificationCategory notificationCategory);

  public void setNotificationId(int notificationId);

  public void setNotificationParameters(List<NotificationParameter> notificationParametersList);

  public void setNotificationType(NotificationType notificationType);

  public void setSentDate(Date sentDate);

}