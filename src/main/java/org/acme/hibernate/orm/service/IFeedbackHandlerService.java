package org.acme.hibernate.orm.service;

import io.vertx.core.eventbus.EventBus;
import io.vertx.ext.web.handler.sockjs.BridgeEvent;

public interface IFeedbackHandlerService {

    void sendFeedbackWeb(BridgeEvent event, EventBus eventBus, String session);
}
