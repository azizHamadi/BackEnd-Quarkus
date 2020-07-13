package org.acme.hibernate.orm.repository.Feedback.Impl;

import org.acme.hibernate.orm.domain.ReponseFeedback;
import org.acme.hibernate.orm.repository.Feedback.IReponseFeedbackRepository;
import org.jboss.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.ws.rs.WebApplicationException;
import java.util.List;

@ApplicationScoped
public class ReponseFeedbackRepository implements IReponseFeedbackRepository {

    private static final Logger LOG = Logger.getLogger(ReponseFeedbackRepository.class);

    @Inject
    EntityManager entityManager;


    @Override
    public List<ReponseFeedback> findAll() {
        return entityManager.createNamedQuery("ReponsesFeedback.findAll", ReponseFeedback.class)
                .getResultList();
    }

    @Override
    public ReponseFeedback findSingle(int id) {
        ReponseFeedback reponseFeedback = entityManager.find(ReponseFeedback.class, id);
        if (reponseFeedback == null) {
            throw new WebApplicationException("ReponseFeedback with id of " + id + " does not exist.", 404);
        }
        return reponseFeedback;
    }

    @Override
    public void create(ReponseFeedback reponseFeedback) {
        entityManager.persist(reponseFeedback);
    }
}