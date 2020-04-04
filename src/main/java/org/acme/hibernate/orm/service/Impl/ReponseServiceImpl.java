package org.acme.hibernate.orm.service.Impl;

import org.acme.hibernate.orm.domain.Reponse;
import org.acme.hibernate.orm.service.ReponseService;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.ws.rs.WebApplicationException;


@Singleton
@Transactional
public class ReponseServiceImpl implements ReponseService {
    private EntityManager entityManager;

    @Inject
    public ReponseServiceImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Reponse update(Long id, Reponse reponse) {
        if (reponse.getText_reponse() == null) {
            throw new WebApplicationException("Reponse Name was not set on request.", 422);
        }
        Reponse entity = entityManager.find(Reponse.class, id);
        if (entity == null) {
            throw new WebApplicationException("Reponse with id of " + id + " does not exist.", 404);
        }
        entity.setCount(entity.getCount()+1);
        return entity;
    }
}
