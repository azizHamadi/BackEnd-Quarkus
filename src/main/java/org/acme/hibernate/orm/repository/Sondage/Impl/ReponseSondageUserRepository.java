package org.acme.hibernate.orm.repository.Sondage.Impl;

import org.acme.hibernate.orm.domain.ReponseSondageUser;
import org.acme.hibernate.orm.domain.Sondage;
import org.acme.hibernate.orm.repository.Sondage.IReponseSondageUserRepository;
import org.jboss.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;

@ApplicationScoped
public class ReponseSondageUserRepository implements IReponseSondageUserRepository {

    private static final Logger LOGGER = Logger.getLogger(ReponseSondageUserRepository.class.getName());

    @Inject
    EntityManager entityManager;

    @Override
    public List<ReponseSondageUser> getReponseUserByReponse(Long id) {
        return entityManager.createQuery("select r from ReponseSondageUser r " +
                "where r.reponseQuestionSondage = " + id , ReponseSondageUser.class).getResultList();
    }

    @Override
    @Transactional
    public void addReponseSondageUser(ReponseSondageUser reponseSondageUser) {
        entityManager.persist(reponseSondageUser);
    }

    @Override
    @Transactional
    public void deleteReponseSondageUser(Long id) {
        ReponseSondageUser reponseSondageUser = entityManager.find(ReponseSondageUser.class,id);
        entityManager.remove(reponseSondageUser);
    }
}
