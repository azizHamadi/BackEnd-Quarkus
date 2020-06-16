package org.acme.hibernate.orm.repository.Sondage;


import org.acme.hibernate.orm.domain.ReponseQuestionSondage;

import java.util.List;

public interface IReponseQuestionSondageRepository {
    List<ReponseQuestionSondage> findAll();
    ReponseQuestionSondage findEventById(Long id);
    void createReponseQuestionSondage(ReponseQuestionSondage reponseQuestionSondage);
    void deleteReponseQuestionSondage(Long id);
}
