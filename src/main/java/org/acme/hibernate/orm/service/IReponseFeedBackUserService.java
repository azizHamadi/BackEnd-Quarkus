package org.acme.hibernate.orm.service;

import org.acme.hibernate.orm.domain.ReponseFeedBackUser;
import java.util.List;

public interface IReponseFeedBackUserService {
    List<ReponseFeedBackUser> findAll();
    ReponseFeedBackUser findSingle(int id);
    void create(ReponseFeedBackUser reponseFeedBackUser);
    List<ReponseFeedBackUser> findByUserQuestion(String idUser, int idQuestion);
}
