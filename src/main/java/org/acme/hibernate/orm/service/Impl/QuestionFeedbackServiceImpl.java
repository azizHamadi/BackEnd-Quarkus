package org.acme.hibernate.orm.service.Impl;

import org.acme.hibernate.orm.domain.QuestionFeedback;
import org.acme.hibernate.orm.repository.Feedback.Impl.QuestionFeedbackRepository;
import org.acme.hibernate.orm.service.IQuestionFeedbackService;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;

@ApplicationScoped
public class QuestionFeedbackServiceImpl implements IQuestionFeedbackService {

    @Inject
    QuestionFeedbackRepository questionFeedbackRepository;

    @Override
    public List<QuestionFeedback> findAll() {
        return questionFeedbackRepository.findAll();
    }

    @Override
    public List<QuestionFeedback> findByFeedback(int id) {
        return questionFeedbackRepository.findByFeedback(id);
    }

    @Override
    public QuestionFeedback findQuestionFeedbckById(int id) {
        return questionFeedbackRepository.findQuestionFeedbckById(id);
    }

    @Override
    public QuestionFeedback createQuestionFeedback(QuestionFeedback questionFeedback) {
        questionFeedbackRepository.createQuestionFeedback(questionFeedback);
        return questionFeedback;
    }
}