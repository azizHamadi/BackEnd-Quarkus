package org.acme.hibernate.orm.repository;

import io.vertx.core.json.JsonObject;
import org.acme.hibernate.orm.domain.QuestionMessage;
import org.jose4j.json.internal.json_simple.JSONObject;

import java.util.List;

public interface IQuestionMessageRepository {
    List<QuestionMessage> findAll();
    QuestionMessage findQuestionMessageById(int id);
    void createQuestionMessage(QuestionMessage questionMessage, Long id);
    List<JSONObject> findByEvent(Long id);
    QuestionMessage findByTextMessage(QuestionMessage questionMessage, Long id);
}
