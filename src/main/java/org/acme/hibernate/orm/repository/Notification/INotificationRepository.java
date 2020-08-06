package org.acme.hibernate.orm.repository.Notification;

import org.acme.hibernate.orm.domain.Notification;
import org.jose4j.json.internal.json_simple.JSONObject;

import java.util.List;

public interface INotificationRepository {

    void createNotification(Notification notification);

    void updateNotification(Notification notification, int id);

    List<Notification> findByEvent(int id);

    List<Notification> findByUser(String id);
}
