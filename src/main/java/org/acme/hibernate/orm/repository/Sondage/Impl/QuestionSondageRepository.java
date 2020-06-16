package org.acme.hibernate.orm.repository.Sondage.Impl;

import org.acme.hibernate.orm.domain.QuestionSondage;
import org.acme.hibernate.orm.repository.Sondage.IQuestionSondageRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.ws.rs.WebApplicationException;
import java.util.List;

@ApplicationScoped
public class QuestionSondageRepository implements IQuestionSondageRepository {

    @Inject
    EntityManager entityManager;

    @Override
    public List<QuestionSondage> findAll() {
        return entityManager.createNamedQuery("QuestionSondage.findAll", QuestionSondage.class)
                .getResultList();
    }

    @Override
    public QuestionSondage findById(Long id) {
        QuestionSondage questionSondage = entityManager.find(QuestionSondage.class, id);
        if (questionSondage == null) {
            throw new WebApplicationException("QuiestionSondage with id of " + id + " does not exist.", 404);
        }
        return questionSondage;
    }

    @Override
    public List<QuestionSondage> findBySondage(Long id) {
        List<QuestionSondage> questionSondages = entityManager.createQuery("select qs from QuestionSondage qs " +
                "where qs.sondage = " + id , QuestionSondage.class).getResultList();;
        if (questionSondages == null) {
            throw new WebApplicationException("QuiestionSondage with id of " + id + " does not exist.", 404);
        }
        return questionSondages;
    }

    @Override
    @Transactional
    public void createQuestionSondage(QuestionSondage questionSondage) {
        entityManager.persist(questionSondage);
    }

    @Override
    @Transactional
    public void deleteQuestionSondage(Long id) {
        QuestionSondage qs = findById(id);
        entityManager.remove(qs);

    }
}
