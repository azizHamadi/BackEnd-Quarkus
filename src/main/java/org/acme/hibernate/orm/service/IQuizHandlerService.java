package org.acme.hibernate.orm.service;

import io.vertx.core.eventbus.EventBus;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.handler.sockjs.BridgeEvent;
import org.acme.hibernate.orm.domain.Question;

import java.util.List;
import java.util.Set;

public interface IQuizHandlerService {

    void sendQuiz(BridgeEvent event, EventBus eventBus, String session);

    void sendResult(BridgeEvent event, EventBus eventBus, String session);

    void sendScore(BridgeEvent event, EventBus eventBus, String session);

    void register(JsonObject body);

    void logout(JsonObject body);

    void generateResult(JsonObject body, String session);

    void sendFromMobile(String session);

    void addSession(Integer key, String user);

    Set<String> addNewUsers(String user);

    void addScore(String session, Integer idQuiz, String user, Integer score);

}
