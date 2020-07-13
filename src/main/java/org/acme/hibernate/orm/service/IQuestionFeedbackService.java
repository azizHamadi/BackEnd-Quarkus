package org.acme.hibernate.orm.service;

import org.acme.hibernate.orm.domain.QuestionFeedback;
import java.util.List;

public interface IQuestionFeedbackService {
    List<QuestionFeedback> findAll();
    List<QuestionFeedback> findByFeedback(int id);
    QuestionFeedback findQuestionFeedbckById(int id);
    QuestionFeedback createQuestionFeedback(QuestionFeedback questionFeedback);
}