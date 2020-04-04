package org.acme.hibernate.orm.service;

import org.acme.hibernate.orm.domain.Question;

import java.util.List;

public interface IQuestionService {

    List<Question> findAll();

    Question findQuestionById(int id);

    void createQuestion(Question question);

    void deleteQuestion(int id);

}
