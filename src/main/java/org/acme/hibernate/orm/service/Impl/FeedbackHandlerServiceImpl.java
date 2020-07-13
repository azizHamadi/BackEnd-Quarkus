package org.acme.hibernate.orm.service.Impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.handler.sockjs.BridgeEvent;
import org.acme.hibernate.orm.PollEnum;
import org.acme.hibernate.orm.domain.Feedback;
import org.acme.hibernate.orm.repository.Feedback.Impl.FeedbackRepository;
import org.acme.hibernate.orm.service.IFeedbackHandlerService;
import org.jboss.logging.Logger;
import javax.inject.Inject;
import javax.inject.Singleton;

import org.riversun.promise.Func;
import org.riversun.promise.Promise;

@Singleton
public class FeedbackHandlerServiceImpl implements IFeedbackHandlerService {

    private static final Logger LOG = Logger.getLogger(FeedbackHandlerServiceImpl.class);

    private final EventBus eventBus;
    private ObjectMapper objectMapper;
    private final FeedbackRepository feedbackRepository;

    @Inject
    public FeedbackHandlerServiceImpl(EventBus eventBus, FeedbackRepository feedbackRepository) {
        this.eventBus = eventBus;
        this.feedbackRepository = feedbackRepository;
        objectMapper = new ObjectMapper();
    }

    @Override
    public void sendFeedbackWeb(BridgeEvent event, EventBus eventBus, String session) {
        LOG.info("feedback : " + session);
        JsonObject jsonObject = event.getRawMessage().getJsonObject("body");
        LOG.info(jsonObject.getString("body"));
        String bodyString = jsonObject.getString("body");
        JsonObject body = new JsonObject(bodyString);
        LOG.info(body.getJsonObject("feedback"));
        try {
            Feedback feedback = objectMapper.readValue(body.getJsonObject("feedback").toString(), Feedback.class);
            Func function1 = (action, data) -> {
                new Thread(() -> {
                    feedbackRepository.updateFeedback(feedback);
                    LOG.info(feedback);
                    //Specify result value.(Any type can be specified)
                    action.resolve("update success");
                }).start();
            };
            Func function2 = (action, data) -> {
                eventBus.publish("client/" + PollEnum.FEEDBACK.toString() + "/" + session, jsonObject);
                action.resolve();
            };
            Promise.resolve()
                    .then(new Promise(function1))
                    .then(new Promise(function2))
                    .start();// start Promise operation

        } catch (JsonProcessingException e) {
            LOG.info(e.getMessage());
            e.printStackTrace();
        }
    }
}
