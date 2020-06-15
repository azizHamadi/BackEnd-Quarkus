package org.acme.hibernate.orm.repository;

import org.acme.hibernate.orm.domain.Question;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.ws.rs.WebApplicationException;
import java.util.List;

@ApplicationScoped
public class QuestionRepository implements IQuestionRepository {

    @Inject
    EntityManager entityManager;

    public List<Question> findAll() {
        return entityManager.createNamedQuery("Questions.findAll", Question.class)
                .getResultList();
    }

    public Question findEventById(int id) {
        Question question = entityManager.find(Question.class, id);
        if (question == null) {
            throw new WebApplicationException("Quiestion with id of " + id + " does not exist.", 404);
        }
        return question;
    }

    @Transactional
    public void createQuestion(Question question) {
        entityManager.persist(question);
    }

    @Transactional
    public void deleteQuestion(int id) {
        Question q = findEventById(id);
        entityManager.remove(q);
    }
}
