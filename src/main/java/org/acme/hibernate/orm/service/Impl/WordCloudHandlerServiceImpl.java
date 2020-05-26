package org.acme.hibernate.orm.service.Impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.handler.sockjs.BridgeEvent;
import org.acme.hibernate.orm.PollEnum;
import org.acme.hibernate.orm.service.IWordCloudHandlerServiceImpl;
import org.jboss.logging.Logger;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class WordCloudHandlerServiceImpl implements IWordCloudHandlerServiceImpl {
    private static final Logger LOG = Logger.getLogger(QuestionHandlerServiceImpl.class);
    private final EventBus eventBus;
    private ObjectMapper objectMapper;

    @Inject
    public WordCloudHandlerServiceImpl(EventBus eventBus) {
        this.eventBus = eventBus;
        this.objectMapper = new ObjectMapper();
    }


    @Override
    public void sendFromMobile(JsonObject body, String session) {
        eventBus.publish("client/" + PollEnum.WORDCLOUD.toString() + "/" + session, body);
    }

    @Override
    public void sendFromWeb(BridgeEvent event, EventBus eventBus, String session) {
        LOG.info(session);
        JsonObject body = event.getRawMessage().getJsonObject("body");
        LOG.info(body);
        eventBus.publish("client/" + PollEnum.WORDCLOUD.toString() + "/" + session, body);
    }
}
