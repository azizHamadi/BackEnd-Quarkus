package org.acme.hibernate.orm.service;

import org.acme.hibernate.orm.domain.ReponseFeedback;

import java.util.List;

public interface IReponseFeedbackService {
    List<ReponseFeedback> findAll();
    ReponseFeedback findSingle(int id);
    void create(ReponseFeedback reponseFeedback);
}
