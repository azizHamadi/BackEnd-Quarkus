import org.acme.hibernate.orm.domain.QuestionMessage;
import org.acme.hibernate.orm.domain.ReponseMessage;
import org.acme.hibernate.orm.repository.IReponseMessageRepository;
import org.jboss.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.ws.rs.WebApplicationException;
import java.util.List;

@ApplicationScoped
public class ReponseMessageRepository implements IReponseMessageRepository {

    private static final Logger LOG = Logger.getLogger(ReponseMessageRepository.class);

    @Inject
    EntityManager entityManager;

    @Override
    public List<ReponseMessage> findAll() {
        return entityManager.createNamedQuery("ReponseMessages.findAll", ReponseMessage.class)
                .getResultList();
    }

    @Override
    public ReponseMessage findReponseMessageById(int id) {
        ReponseMessage reponseMessage = entityManager.find(ReponseMessage.class, id);
        if (reponseMessage == null) {
            throw new WebApplicationException("reponseMessage with id of " + id + " does not exist.", 404);
        }
        return reponseMessage;
    }

    @Override
    public void createReponseMessage(ReponseMessage reponseMessage) {
        entityManager.persist(reponseMessage);
    }

    @Override
    public void deleteReponseMessage(int id) {
        ReponseMessage rM = findReponseMessageById(id);
        entityManager.remove(rM);
    }

    @Override
    public List<ReponseMessage> findByEvent(int id) {
        List<ReponseMessage> reponseMessages = entityManager.createQuery("select rM from ReponseMessage rM " +
                "where rM.event = " + id , ReponseMessage.class).getResultList();
        return reponseMessages ;
    }

    @Transactional
    @Override
    public List<ReponseMessage> findByEventQuestion(Long id,String text_message) {
        QuestionMessage questionMessage = entityManager.createQuery("select qM from QuestionMessage qM " +
                        "where qM.event = " + id + " and qM.text_message = '" + text_message + "'"
                , QuestionMessage.class).getSingleResult();
        LOG.info(questionMessage);
        List<ReponseMessage> reponseMessages= entityManager.createQuery("select rM from ReponseMessage rM " +
                "where rM.questionMessage = " + questionMessage.getId_questionMessage() , ReponseMessage.class).getResultList();
        return reponseMessages ;
    }

}