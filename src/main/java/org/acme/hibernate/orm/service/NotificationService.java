package org.acme.hibernate.orm.service;

import io.vertx.core.eventbus.EventBus;
import io.vertx.ext.web.handler.sockjs.BridgeEvent;
import org.acme.hibernate.orm.domain.Question;

public interface NotificationService {

    void onOpen(BridgeEvent event, EventBus eventBus);

    void onMessage(BridgeEvent event, EventBus eventBus);

    void onClose(BridgeEvent event, EventBus eventBus);


}