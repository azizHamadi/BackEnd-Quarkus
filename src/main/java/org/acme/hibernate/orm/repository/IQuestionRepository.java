package org.acme.hibernate.orm.repository;

import org.acme.hibernate.orm.domain.Question;

import java.util.List;

public interface IQuestionRepository {
    List<Question> findAll();
    Question findEventById(int id);
    void createQuestion(Question question);
    void deleteQuestion(int id);
}
