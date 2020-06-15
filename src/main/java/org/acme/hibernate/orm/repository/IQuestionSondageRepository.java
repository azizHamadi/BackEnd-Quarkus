package org.acme.hibernate.orm.repository;


import org.acme.hibernate.orm.domain.QuestionSondage;

import java.util.List;

public interface IQuestionSondageRepository {
    List<QuestionSondage> findAll();
    QuestionSondage findById(Long id);
    List<QuestionSondage> findBySondage(Long id);
    void createQuestionSondage(QuestionSondage questionSondage);
    void deleteQuestionSondage(Long id);
}
