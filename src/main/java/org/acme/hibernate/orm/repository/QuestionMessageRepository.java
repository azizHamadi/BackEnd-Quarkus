package org.acme.hibernate.orm.repository;

import io.vertx.core.json.Json;
import org.acme.hibernate.orm.domain.Question;
import org.acme.hibernate.orm.domain.QuestionMessage;
import org.acme.hibernate.orm.domain.Quiz;
import org.acme.hibernate.orm.domain.ReponseMessage;
import org.acme.hibernate.orm.service.Impl.QuestionHandlerServiceImpl;
import org.jboss.logging.Logger;
import org.jose4j.json.internal.json_simple.JSONObject;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.ws.rs.WebApplicationException;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class QuestionMessageRepository implements IQuestionMessageRepository {
    private static final Logger LOG = Logger.getLogger(QuestionHandlerServiceImpl.class);

    @Inject
    IReponseMessageRepository reponseMessageRepository;

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
    public void createQuestionMessage(QuestionMessage questionMessage, Long id) {
        LOG.info(questionMessage);
        questionMessage.setVerify(!QuestionHandlerServiceImpl.moderateurSession.get(id.toString()));
        List<QuestionMessage> newQuestionMessage = entityManager.createQuery("select qM from QuestionMessage qM " +
                "where qM.event = " + id + " and qM.text_message = '" + questionMessage.getText_message() + "'"
                , QuestionMessage.class).getResultList();
        if( newQuestionMessage.size() == 0 ) {
            entityManager.persist(questionMessage);
        }
    }

    @Transactional
    public List<JSONObject> findByEvent(Long id) {
        List<JSONObject> listQuestion = new ArrayList<>();
        List<QuestionMessage> questionMessages = entityManager.createQuery("select qM from QuestionMessage qM " +
                "where qM.event = " + id , QuestionMessage.class).getResultList();
        questionMessages.forEach(q -> {
            List<ReponseMessage> reponseMessages = reponseMessageRepository.findByEventQuestion(id,q.getText_message());
            JSONObject questionObject = new JSONObject();
            questionObject.put("question", q);
            questionObject.put("reponses", reponseMessages);
            listQuestion.add(questionObject);
        });
        return listQuestion ;
    }

}
