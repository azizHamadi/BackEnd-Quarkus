package org.acme.hibernate.orm.service;

import org.acme.hibernate.orm.domain.Notification;

import java.util.List;

public interface INotificationService {

    void createNotification(Notification notification);

    void updateNotification(Notification notification, int id);

    List<Notification> findByEvent(int id);

    List<Notification> findByUser(String id);
}
