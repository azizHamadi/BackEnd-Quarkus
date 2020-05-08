package org.acme.hibernate.orm.repository;

import org.acme.hibernate.orm.domain.QuestionMessage;

import java.util.List;

public interface IQuestionMessageRepository {
    List<QuestionMessage> findAll();
    QuestionMessage findQuestionMessageById(int id);
    void createQuestionMessage(QuestionMessage questionMessage, Long id);
    List<QuestionMessage> findByEvent(Long id);
}
