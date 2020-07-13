package org.acme.hibernate.orm.repository.Feedback.Impl;

import org.acme.hibernate.orm.domain.*;
import org.acme.hibernate.orm.repository.Feedback.IFeedbackRepository;
import org.jose4j.json.internal.json_simple.JSONObject;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.ws.rs.WebApplicationException;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class FeedbackRepository implements IFeedbackRepository {

    @Inject
    EntityManager entityManager;

    @Inject
    QuestionFeedbackRepository questionFeedbackRepository;

    @Override
    @Transactional
    public List<Feedback> findAll() {
        return entityManager.createNamedQuery("Feedback.findAll", Feedback.class)
                .getResultList();
    }

    @Override
    @Transactional
    public Feedback findFeedbackById(int id) {
        Feedback feedback = entityManager.find(Feedback.class, id);
        if (feedback == null) {
            throw new WebApplicationException("feedback with id of " + id + " does not exist.", 404);
        }
        return feedback;
    }


    @Override
    public JSONObject getFeedbackById(int id) {
        Feedback feedback = entityManager.find(Feedback.class, id);
        if (feedback == null) {
            throw new WebApplicationException("Feedback with id of " + id + " does not exist.", 404);
        }
        List<QuestionFeedback> questionFeedbacks = this.questionFeedbackRepository.findByFeedback(feedback.getId());
        JSONObject feedback1 = new JSONObject();
        feedback1.put("sondage",feedback);
        feedback1.put("questions", questionFeedbacks);
        return feedback1;
    }

    @Override
    @Transactional
    public List<JSONObject> findByEvent(int id) {
        List<Feedback> feedbacks = entityManager.createQuery("select f from Feedback f " +
                "where f.status = true and f.event = " + id , Feedback.class).getResultList();
        List<JSONObject> feedbacksObject = new ArrayList<>();
        feedbacks.forEach(f-> {
            List<QuestionFeedback> questionFeedbacks = this.questionFeedbackRepository.findByFeedback(f.getId());
            JSONObject feedback = new JSONObject();
            feedback.put("feedback",f);
            feedback.put("questions", questionFeedbacks);
            feedbacksObject.add(feedback);
        });
        return feedbacksObject ;
    }
    @Override
    public List<JSONObject> findByEventW(int id) {
        List<Feedback> feedbacks = entityManager.createQuery("select f from Sondage f " +
                "where f.event = " + id , Feedback.class).getResultList();
        List<JSONObject> feedbcksObject = new ArrayList<>();
        feedbacks.forEach(f-> {
            List<QuestionFeedback> questionFeedbacks = this.questionFeedbackRepository.findByFeedback(f.getId());
            JSONObject feedback = new JSONObject();
            feedback.put("feedback",f);
            feedback.put("questions", questionFeedbacks);
            feedbcksObject.add(feedback);
        });
        return feedbcksObject ;
    }

    @Override
    @Transactional
    public Feedback updateFeedback(Feedback feedback) {
        Feedback feedback1 = entityManager.find(Feedback.class,feedback.getId());
        feedback1.setStatus(true);
        entityManager.flush();
        return feedback1;
    }

    @Override
    @Transactional
    public Feedback createFeedback(Feedback feedback) {
        entityManager.persist(feedback);
        return feedback;
    }
}