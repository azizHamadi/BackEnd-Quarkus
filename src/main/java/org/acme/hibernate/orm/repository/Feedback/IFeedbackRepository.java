package org.acme.hibernate.orm.repository.Feedback;

import org.acme.hibernate.orm.domain.Feedback;
import org.jose4j.json.internal.json_simple.JSONObject;

import java.util.List;

public interface IFeedbackRepository {
    List<Feedback> findAll();
    Feedback findFeedbackById(int id);
    List<JSONObject> findByEvent(int id);
    Feedback createFeedback(Feedback feedback);
    JSONObject getFeedbackById(int id);
    List<JSONObject> findByEventW(int id);
    Feedback updateFeedback(Feedback feedback);
    List<Feedback> findByEventObject(int id);
}