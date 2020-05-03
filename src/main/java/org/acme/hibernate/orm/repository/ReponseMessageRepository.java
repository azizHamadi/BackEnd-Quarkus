package org.acme.hibernate.orm.repository;

import org.acme.hibernate.orm.domain.Quiz;
import org.acme.hibernate.orm.domain.ReponseMessage;
import org.jboss.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.ws.rs.WebApplicationException;
import java.util.List;

@ApplicationScoped
public class ReponseMessageRepository implements IReponseMessageRepository {

    private static final Logger LOG = Logger.getLogger(ReponseMessageRepository.class);

    @Inject
    EntityManager entityManager;

    @Override
    public List<ReponseMessage> findAll() {
        return entityManager.createNamedQuery("ReponseMessages.findAll", ReponseMessage.class)
                .getResultList();
    }

    @Override
    public ReponseMessage findReponseMessageById(int id) {
        ReponseMessage reponseMessage = entityManager.find(ReponseMessage.class, id);
        if (reponseMessage == null) {
            throw new WebApplicationException("reponseMessage with id of " + id + " does not exist.", 404);
        }
        return reponseMessage;
    }

    @Override
    public void createReponseMessage(ReponseMessage reponseMessage) {
        entityManager.persist(reponseMessage);
    }

    @Override
    public void deleteReponseMessage(int id) {
        ReponseMessage rM = findReponseMessageById(id);
        entityManager.remove(rM);
    }

    @Override
    public List<ReponseMessage> findByEvent(int id) {
        List<ReponseMessage> reponseMessages = entityManager.createQuery("select rM from ReponseMessage rM " +
                "where rM.event = " + id , ReponseMessage.class).getResultList();
        return reponseMessages ;
    }
}
