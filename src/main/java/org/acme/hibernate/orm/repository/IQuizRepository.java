package org.acme.hibernate.orm.repository;


import org.acme.hibernate.orm.domain.Quiz;

import java.util.List;

public interface IQuizRepository {
    List<Quiz> findAll();
    Quiz findQuizById(int id);
    void createQuiz(Quiz quiz);
    void deleteQuiz(int id);
    List<Quiz> findByEvent(int id);
}
