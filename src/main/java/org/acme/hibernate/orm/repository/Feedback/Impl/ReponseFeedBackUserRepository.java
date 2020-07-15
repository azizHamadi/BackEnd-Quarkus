package org.acme.hibernate.orm.repository.Feedback.Impl;

import org.acme.hibernate.orm.domain.ReponseFeedBackUser;
import org.acme.hibernate.orm.domain.ReponseFeedback;
import org.acme.hibernate.orm.repository.Feedback.IReponseFeedBackUserRepository;
import org.jboss.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;

@ApplicationScoped
public class ReponseFeedBackUserRepository implements IReponseFeedBackUserRepository {

    private static final Logger LOG = Logger.getLogger(ReponseFeedBackUserRepository.class);

    @Inject
    EntityManager entityManager;

    @Override
    public List<ReponseFeedBackUser> findAll() {
        return entityManager.createNamedQuery("reponseFeedBackUser.findAll", ReponseFeedBackUser.class)
                .getResultList();
    }

    @Override
    public ReponseFeedBackUser findSingle(int id) {
        return entityManager.find(ReponseFeedBackUser.class,id);
    }

    @Override
    @Transactional
    public void create(ReponseFeedBackUser reponseFeedbackUser) {
        entityManager.persist(reponseFeedbackUser);
    }
}
