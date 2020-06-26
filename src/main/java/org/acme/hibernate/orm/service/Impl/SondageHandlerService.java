package org.acme.hibernate.orm.service.Impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.handler.sockjs.BridgeEvent;
import org.acme.hibernate.orm.PollEnum;
import org.acme.hibernate.orm.domain.Question;
import org.acme.hibernate.orm.service.ISondageHandlerService;
import org.jboss.logging.Logger;

import javax.inject.Inject;

public class SondageHandlerService implements ISondageHandlerService {

    private static final Logger LOG = Logger.getLogger(QuizHandlerServiceImpl.class);

    private final EventBus eventBus;
    private ObjectMapper objectMapper;

    @Inject
    public SondageHandlerService(EventBus eventBus) {
        this.eventBus = eventBus;
        this.objectMapper = new ObjectMapper();
    }

    @Override
    public void sendResultMobile(JsonObject body, String session) {

    }

    @Override
    public void generateResult(JsonObject body, String session) {

    }

    @Override
    public void sendSondageWeb(BridgeEvent event, EventBus eventBus, String session) {
        LOG.info("sondage : " + session);
        JsonObject jsonObject = event.getRawMessage().getJsonObject("body");
        LOG.info(jsonObject.getString("body"));
        eventBus.publish("client/" + PollEnum.SONDAGE.toString() + "/" + session, jsonObject);
    }
}
