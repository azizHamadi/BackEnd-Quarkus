package org.acme.hibernate.orm.repository.Feedback.Impl;

import org.acme.hibernate.orm.domain.Feedback;
import org.acme.hibernate.orm.domain.QuestionFeedback;
import org.acme.hibernate.orm.domain.ReponseFeedBackUser;
import org.acme.hibernate.orm.repository.Feedback.IReponseFeedBackUserRepository;
import org.jboss.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class ReponseFeedBackUserRepository implements IReponseFeedBackUserRepository {

    private static final Logger LOG = Logger.getLogger(ReponseFeedBackUserRepository.class);

    @Inject
    EntityManager entityManager;

    @Inject
    QuestionFeedbackRepository questionFeedbackRepository;

    @Inject
    FeedbackRepository feedbackRepository;

    @Override
    public List<ReponseFeedBackUser> findAll() {
        return entityManager.createNamedQuery("reponseFeedBackUser.findAll", ReponseFeedBackUser.class)
                .getResultList();
    }

    @Override
    public ReponseFeedBackUser findSingle(int id) {
        return entityManager.find(ReponseFeedBackUser.class,id);
    }

    @Override
    public List<ReponseFeedBackUser> findByUserQuestion(String idUser, int idevent) {
        List<Feedback> feedbacks = feedbackRepository.findByEventObject(idevent);
        if( feedbacks.size() > 0){
            List<QuestionFeedback> questionFeedbacks = questionFeedbackRepository.findByFeedback(feedbacks.get(0).getId());
            if(questionFeedbacks.size() > 0 ){
                LOG.info(questionFeedbacks.get(0).getId());
                List<ReponseFeedBackUser> reponseFeedBackUsers = entityManager.createQuery("select r from ReponseFeedBackUser r " +
                        "where r.userDTO = '" + idUser + "' and r.questionFeedback = " + questionFeedbacks.get(0).getId() , ReponseFeedBackUser.class).getResultList();
                LOG.info(reponseFeedBackUsers);
                return reponseFeedBackUsers;
            }
        }
        return new ArrayList<>();
    }

    @Override
    @Transactional
    public void create(ReponseFeedBackUser reponseFeedbackUser) {
        entityManager.persist(reponseFeedbackUser);
    }
}
