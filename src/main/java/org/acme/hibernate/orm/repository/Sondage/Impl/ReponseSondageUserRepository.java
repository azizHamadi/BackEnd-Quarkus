package org.acme.hibernate.orm.repository.Sondage.Impl;

import org.acme.hibernate.orm.domain.ReponseSondageUser;
import org.acme.hibernate.orm.repository.Sondage.IReponseSondageUserRepository;
import org.jboss.logging.Logger;

import javax.inject.Inject;
import javax.persistence.EntityManager;

public class ReponseSondageUserRepository implements IReponseSondageUserRepository {

    private static final Logger LOGGER = Logger.getLogger(ReponseSondageUserRepository.class.getName());

    @Inject
    EntityManager entityManager;

    @Inject
    ReponseQuestionSondageRepository reponseQuestionSondageRepository;

    @Override
    public void addReponseSondageUser(ReponseSondageUser reponseSondageUser, Long id) {

    }

    @Override
    public void deleteReponseSondageUser(ReponseSondageUser reponseSondageUser, Long id) {

    }
}
