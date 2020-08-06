package org.acme.hibernate.orm.repository.Feedback;

import org.acme.hibernate.orm.domain.ReponseFeedBackUser;

import java.util.List;

public interface IReponseFeedBackUserRepository {
    List<ReponseFeedBackUser> findAll();
    ReponseFeedBackUser findSingle(int id);
    List<ReponseFeedBackUser> findByUserQuestion(String idUser, int idQuestion);
    void create(ReponseFeedBackUser reponseFeedbackUser);
}
