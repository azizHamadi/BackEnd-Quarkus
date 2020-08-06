package org.acme.hibernate.orm.repository.Notification.Impl;

import org.acme.hibernate.orm.domain.Notification;
import org.acme.hibernate.orm.repository.Notification.INotificationRepository;
import org.jboss.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;

@ApplicationScoped
public class NotificationRepository implements INotificationRepository {

    private static final Logger LOG = Logger.getLogger(NotificationRepository.class);

    @Inject
    EntityManager entityManager;

    @Override
    @Transactional
    public void createNotification(Notification notification) {
        entityManager.persist(notification);
    }

    @Override
    @Transactional
    public void updateNotification(Notification notification, int id) {
        Notification notification1 = entityManager.createQuery("select n from Notification n " +
                "where n.event = " + id + " and n.userDTO = '" + notification.getUserDTO().getId() + "'" , Notification.class).getSingleResult();
        notification1.setStatus(notification.getStatus());
        entityManager.flush();
    }

    @Override
    public List<Notification> findByEvent(int id) {
        List<Notification> notifications = entityManager.createQuery("select n from Notification n " +
                "where n.event = " + id, Notification.class).getResultList();
        return notifications;
    }

    @Override
    public List<Notification> findByUser(String id) {
        List<Notification> notifications = entityManager.createQuery("select n from Notification n " +
                "where n.userDTO = '" + id + "' ORDER BY n.date desc", Notification.class).getResultList();
        return notifications;
    }
}
