package org.acme.hibernate.orm.service.Impl;

import org.acme.hibernate.orm.domain.Question;
import org.acme.hibernate.orm.repository.QuestionRepository;
import org.acme.hibernate.orm.service.IQuestionService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;

@ApplicationScoped
public class QuestionServiceImpl implements IQuestionService {
    @Inject
    QuestionRepository questionRepository;

    @Override
    public List<Question> findAll() {
        return questionRepository.findAll();
    }

    @Override
    public Question findQuestionById(int id) {
        return questionRepository.findEventById(id);
    }

    @Override
    public void createQuestion(Question question) {
        questionRepository.createQuestion(question);
    }

    @Override
    public void deleteQuestion(int id) {
        questionRepository.deleteQuestion(id);
    }

}
