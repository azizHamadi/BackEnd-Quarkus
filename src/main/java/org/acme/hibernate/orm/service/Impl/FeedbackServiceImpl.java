package org.acme.hibernate.orm.service.Impl;

import org.acme.hibernate.orm.domain.Feedback;
import org.acme.hibernate.orm.repository.Feedback.Impl.FeedbackRepository;
import org.acme.hibernate.orm.service.IFeedbackService;
import org.jose4j.json.internal.json_simple.JSONObject;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;

@ApplicationScoped
public class FeedbackServiceImpl implements IFeedbackService {

    @Inject
    FeedbackRepository feedbackRepository;

    @Override
    public List<Feedback> findAll() {
        return feedbackRepository.findAll();
    }

    @Override
    public Feedback findFeedbackById(int id) {
        return feedbackRepository.findFeedbackById(id);
    }

    @Override
    public List<JSONObject> findByEvent(int id) {
        return feedbackRepository.findByEvent(id);
    }

    @Override
    public Feedback createFeedback(Feedback feedback) {
        return    feedbackRepository.createFeedback(feedback);
    }

    @Override
    public List<JSONObject> findByEventW(int id) {
        return feedbackRepository.findByEventW(id);
    }

    @Override
    public JSONObject getFeedbackById(int id) {
        return feedbackRepository.getFeedbackById(id);
    }
}
