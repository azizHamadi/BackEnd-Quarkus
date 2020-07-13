package org.acme.hibernate.orm.repository.Feedback;

import org.acme.hibernate.orm.domain.QuestionFeedback;
import java.util.List;

public interface IQuestionFeedbackRepository {
    List<QuestionFeedback> findAll();
    List<QuestionFeedback> findByFeedback(int id);
    QuestionFeedback findQuestionFeedbckById(int id);
    QuestionFeedback createQuestionFeedback(QuestionFeedback questionFeedback);
}
