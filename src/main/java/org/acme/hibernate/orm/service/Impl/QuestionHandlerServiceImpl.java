package org.acme.hibernate.orm.service.Impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.handler.sockjs.BridgeEvent;
import org.acme.hibernate.orm.PollEnum;
import org.acme.hibernate.orm.domain.QuestionMessage;
import org.acme.hibernate.orm.repository.QuestionMessageRepository;
import org.acme.hibernate.orm.service.IQuestionHandlerService;
import org.jboss.logging.Logger;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class QuestionHandlerServiceImpl implements IQuestionHandlerService {

    private static final Logger LOG = Logger.getLogger(QuestionHandlerServiceImpl.class);
    private final EventBus eventBus;
    private ObjectMapper objectMapper;
    private final QuestionMessageRepository questionMessageRepository;

    @Inject
    public QuestionHandlerServiceImpl(EventBus eventBus, QuestionMessageRepository questionMessageRepository) {
        this.eventBus = eventBus;
        objectMapper = new ObjectMapper();
        this.questionMessageRepository =  questionMessageRepository;
    }

    @Override
    public void sendFromMobile(JsonObject body, String session) {
        LOG.info("question id event : " + session);
        LOG.info(body);
        QuestionMessage questionMessage = null;
        try {
            questionMessage = objectMapper.readValue(body.toString(), QuestionMessage.class);
            LOG.info(questionMessage);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        eventBus.publish("client/" + PollEnum.QUESTION.toString() + "/" + session, body);
    }

    @Override
    public void sendFromWeb(BridgeEvent event, EventBus eventBus, String session) {
        JsonObject body = event.getRawMessage().getJsonObject("body");
        LOG.info(body.getString("body"));
        QuestionMessage questionMessage = null;
        try {
            questionMessage = objectMapper.readValue(body.getString("body"), QuestionMessage.class);
            LOG.info(questionMessage);
            //this.questionMessageRepository.createQuestionMessage(questionMessage);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        eventBus.publish("client/" + PollEnum.QUESTION.toString() + "/" + session, body);
    }

}
