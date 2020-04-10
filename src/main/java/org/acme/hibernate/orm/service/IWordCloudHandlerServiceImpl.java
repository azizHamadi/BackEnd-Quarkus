package org.acme.hibernate.orm.service;

import io.vertx.core.eventbus.EventBus;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.handler.sockjs.BridgeEvent;

public interface IWordCloudHandlerServiceImpl {

    void sendFromMobile(JsonObject body, String session);

    void sendFromWeb(BridgeEvent event, EventBus eventBus, String session);

}
