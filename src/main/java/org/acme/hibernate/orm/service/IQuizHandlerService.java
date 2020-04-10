package org.acme.hibernate.orm.service;

import io.vertx.core.eventbus.EventBus;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.handler.sockjs.BridgeEvent;
import org.acme.hibernate.orm.domain.Question;

import java.util.List;

public interface IQuizHandlerService {

    void sendQuiz(BridgeEvent event, EventBus eventBus, String session);

    void sendResult(BridgeEvent event, EventBus eventBus, String session);

    void register(JsonObject body);

    void generateResult(JsonObject body, String session);

    void sendFromMobile(String session);

    void addSession(Integer key, String user);

    List<String> addNewUsers(String user);

}
