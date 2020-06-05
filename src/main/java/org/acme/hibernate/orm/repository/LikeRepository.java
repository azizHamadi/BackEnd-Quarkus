package org.acme.hibernate.orm.repository;

import org.acme.hibernate.orm.domain.Aime;
import org.acme.hibernate.orm.domain.QuestionMessage;
import org.acme.hibernate.orm.domain.Quiz;
import org.acme.hibernate.orm.domain.UserDTO;
import org.acme.hibernate.orm.web.rest.EventResource;
import org.acme.hibernate.orm.web.rest.LikesResource;
import org.jboss.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;

@ApplicationScoped
public class LikeRepository implements ILikeRepository {

    private static final Logger LOGGER = Logger.getLogger(LikesResource.class.getName());

    @Inject
    EntityManager entityManager;

    @Inject
    QuestionMessageRepository questionMessageRepository;

    @Override
    public List<Aime> getLikebyQuestion(QuestionMessage questionMessage, Long id) {
        QuestionMessage question = questionMessageRepository.findByTextMessage(questionMessage, id);
        return  entityManager.createQuery("select l from Aime l " +
                "where l.messageQuestion = " + question.getId_questionMessage() , Aime.class).getResultList();
    }

    @Override
    @Transactional
    public void addLike(Aime like, Long id) {
        QuestionMessage questionMessage = like.getMessageQuestion();
        QuestionMessage question = questionMessageRepository.findByTextMessage(questionMessage, id);
        Aime newLike = new Aime();
        newLike.setMessageQuestion(question);
        newLike.setUserDTO(like.getUserDTO());
        entityManager.persist(newLike);
    }

    @Override
    @Transactional
    public void deleteLike(Aime like, Long id) {
        QuestionMessage questionMessage = like.getMessageQuestion();
        QuestionMessage question = questionMessageRepository.findByTextMessage(questionMessage, id);
        Aime deletedLike = entityManager.createQuery("select l from Aime l " +
                "where l.messageQuestion = " + question.getId_questionMessage() + " and l.userDTO = '" + like.getUserDTO().getId() + "'" , Aime.class).getSingleResult();
        entityManager.remove(deletedLike);
    }
}
