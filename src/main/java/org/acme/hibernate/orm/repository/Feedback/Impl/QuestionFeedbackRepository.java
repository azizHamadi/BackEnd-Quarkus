package org.acme.hibernate.orm.repository.Feedback.Impl;

import org.acme.hibernate.orm.domain.QuestionFeedback;
import org.acme.hibernate.orm.repository.Feedback.IQuestionFeedbackRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.ws.rs.WebApplicationException;
import java.io.Serializable;
import java.util.List;

@ApplicationScoped
public class QuestionFeedbackRepository implements IQuestionFeedbackRepository {
    @Inject
    EntityManager entityManager;

    @Override
    public List<QuestionFeedback> findAll() {
        return entityManager.createNamedQuery("QuestionsFeedback.findAll", QuestionFeedback.class)
                .getResultList();
    }

    @Override
    @Transactional
    public List<QuestionFeedback> findByFeedback(int id) {
        List<QuestionFeedback> questionFeedbacks = entityManager.createQuery("select q from QuestionFeedback q " +
                "where q.feedback = " + id , QuestionFeedback.class).getResultList();
        return questionFeedbacks ;
    }

    @Override
    @Transactional
    public QuestionFeedback findQuestionFeedbckById(int id) {
        QuestionFeedback questionFeedback= entityManager.find(QuestionFeedback.class, id);
        if (questionFeedback== null) {
            throw new WebApplicationException("QuestionFeedback with id of " + id + " does not exist.", 404);
        }
        return questionFeedback;
    }

    @Override
    @Transactional
    public QuestionFeedback createQuestionFeedback(QuestionFeedback questionFeedback) {
        entityManager.persist(questionFeedback);
        return questionFeedback;
    }
}