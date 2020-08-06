package org.acme.hibernate.orm.service.Impl;

import org.acme.hibernate.orm.domain.Notification;
import org.acme.hibernate.orm.repository.Notification.Impl.NotificationRepository;
import org.acme.hibernate.orm.service.INotificationService;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;

@ApplicationScoped
public class NotificationFeedbackService implements INotificationService {

    @Inject
    NotificationRepository notificationRepository;

    @Override
    public void createNotification(Notification notification) {
        notificationRepository.createNotification(notification);
    }

    @Override
    public void updateNotification(Notification notification, int id) {
        notificationRepository.updateNotification(notification,id);
    }

    @Override
    public List<Notification> findByEvent(int id) {
        return notificationRepository.findByEvent(id);
    }

    @Override
    public List<Notification> findByUser(String id) {
        return notificationRepository.findByUser(id);
    }
}
