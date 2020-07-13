package org.acme.hibernate.orm.service;

import org.acme.hibernate.orm.domain.Feedback;
import org.jose4j.json.internal.json_simple.JSONObject;

import java.util.List;

public interface IFeedbackService {
    List<Feedback> findAll();
    Feedback findFeedbackById(int id);
    List<JSONObject> findByEvent(int id);
    Feedback createFeedback(Feedback feedback);
    List<JSONObject> findByEventW(int id);
    public JSONObject getFeedbackById(int id);

}