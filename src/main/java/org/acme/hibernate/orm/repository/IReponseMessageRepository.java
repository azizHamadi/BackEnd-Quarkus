package org.acme.hibernate.orm.repository;

import org.acme.hibernate.orm.domain.QuestionMessage;
import org.acme.hibernate.orm.domain.ReponseMessage;

import java.util.List;

public interface IReponseMessageRepository {
    List<ReponseMessage> findAll();
    ReponseMessage findReponseMessageById(int id);
    void createReponseMessage(ReponseMessage reponseMessage);
    void deleteReponseMessage(int id);
    List<ReponseMessage> findByEvent(int id);
    List<ReponseMessage> findByEventQuestion(Long id,String text_message);
}
