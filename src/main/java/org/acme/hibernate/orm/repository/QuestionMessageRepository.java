package org.acme.hibernate.orm.repository;

import org.acme.hibernate.orm.domain.Question;
import org.acme.hibernate.orm.domain.QuestionMessage;
import org.acme.hibernate.orm.service.Impl.QuestionHandlerServiceImpl;
import org.jboss.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.ws.rs.WebApplicationException;
import java.util.List;

@ApplicationScoped
public class QuestionMessageRepository implements IQuestionMessageRepository {
    private static final Logger LOG = Logger.getLogger(QuestionHandlerServiceImpl.class);

    @Inject
    EntityManager entityManager;

    @Transactional
    public List<QuestionMessage> findAll() {
        return entityManager.createNamedQuery("QuestionMessages.findAll", QuestionMessage.class)
                .getResultList();
    }

    @Transactional
    public QuestionMessage findQuestionMessageById(int id) {
        QuestionMessage questionMessage = entityManager.find(QuestionMessage.class, id);
        if (questionMessage == null) {
            throw new WebApplicationException("QuiestionMessage with id of " + id + " does not exist.", 404);
        }
        return questionMessage;
    }

    @Transactional
    public void createQuestionMessage(QuestionMessage questionMessage) {
        LOG.info(questionMessage);
        entityManager.persist(questionMessage);
    }
}
