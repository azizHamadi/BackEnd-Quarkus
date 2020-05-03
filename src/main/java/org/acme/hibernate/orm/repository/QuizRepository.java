package org.acme.hibernate.orm.repository;

import org.acme.hibernate.orm.SockJsExample;
import org.acme.hibernate.orm.domain.Event;
import org.acme.hibernate.orm.domain.Question;
import org.acme.hibernate.orm.domain.Quiz;
import org.jboss.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.ws.rs.WebApplicationException;
import java.util.List;

@ApplicationScoped
public class QuizRepository implements IQuizRepository {
    private static final Logger LOG = Logger.getLogger(QuizRepository.class);

    @Inject
    EntityManager entityManager;

    @Override
    public List<Quiz> findAll() {
        return entityManager.createNamedQuery("Quiz.findAll", Quiz.class)
                .getResultList();
    }

    @Override
    public Quiz findQuizById(int id) {
        Quiz quiz = entityManager.find(Quiz.class, id);
        if (quiz == null) {
            throw new WebApplicationException("quiz with id of " + id + " does not exist.", 404);
        }
        return quiz;
    }

    @Override
    @Transactional
    public void createQuestion(Quiz quiz) {
        entityManager.persist(quiz);
    }

    @Override
    public void deleteQuestion(int id) {
        Quiz q = findQuizById(id);
        entityManager.remove(q);
    }

    @Override
    public List<Quiz> findByEvent(int id) {
        List<Quiz> quiz = entityManager.createQuery("select q from Quiz q " +
                "where q.event = " + id , Quiz.class).getResultList();
        return quiz ;
    }

}
