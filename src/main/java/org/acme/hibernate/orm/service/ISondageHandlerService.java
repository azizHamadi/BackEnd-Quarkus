package org.acme.hibernate.orm.service;

import io.vertx.core.eventbus.EventBus;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.handler.sockjs.BridgeEvent;

public interface ISondageHandlerService {

    void sendResultMobile(JsonObject body, String session);

    void generateResult(JsonObject body, String session);

    void sendSondageWeb(BridgeEvent event, EventBus eventBus, String session);

}
