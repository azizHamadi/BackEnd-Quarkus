package org.acme.hibernate.orm.repository;

import org.acme.hibernate.orm.domain.QuestionSondage;
import org.acme.hibernate.orm.domain.ReponseQuestionSondage;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.ws.rs.WebApplicationException;
import java.util.List;

@ApplicationScoped
public class ReponseQuestionSondageRepository implements IReponseQuestionSondageRepository {

    @Inject
    EntityManager entityManager;


    @Override
    public List<ReponseQuestionSondage> findAll() {
        return entityManager.createNamedQuery("ReponseQuestionSondage.findAll", ReponseQuestionSondage.class)
                .getResultList();
    }

    @Override
    public ReponseQuestionSondage findEventById(Long id) {
        ReponseQuestionSondage reponseQuestionSondage = entityManager.find(ReponseQuestionSondage.class, id);
        if (reponseQuestionSondage == null) {
            throw new WebApplicationException("ReponseQuestionSondage with id of " + id + " does not exist.", 404);
        }
        return reponseQuestionSondage;
    }

    @Override
    @Transactional
    public void createReponseQuestionSondage(ReponseQuestionSondage reponseQuestionSondage) {
        entityManager.persist(reponseQuestionSondage);
    }

    @Override
    @Transactional
    public void deleteReponseQuestionSondage(Long id) {
        ReponseQuestionSondage rqs = findEventById(id);
        entityManager.remove(rqs);
    }
}
