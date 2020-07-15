package org.acme.hibernate.orm.repository.Feedback;

import org.acme.hibernate.orm.domain.ReponseFeedBackUser;

import java.util.List;

public interface IReponseFeedBackUserRepository {
    List<ReponseFeedBackUser> findAll();
    ReponseFeedBackUser findSingle(int id);
    void create(ReponseFeedBackUser reponseFeedbackUser);
}
