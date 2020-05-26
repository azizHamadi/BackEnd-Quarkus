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
import java.util.HashMap;
import java.util.Map;

@Singleton
public class QuestionHandlerServiceImpl implements IQuestionHandlerService {

    private static final Logger LOG = Logger.getLogger(QuestionHandlerServiceImpl.class);
    private final EventBus eventBus;
    private ObjectMapper objectMapper;
    private final QuestionMessageRepository questionMessageRepository;
    public static Map<String, Boolean> moderateurSession = new HashMap<>();

    @Inject
    public QuestionHandlerServiceImpl(EventBus eventBus, QuestionMessageRepository questionMessageRepository) {
        this.eventBus = eventBus;
        objectMapper = new ObjectMapper();
        this.questionMessageRepository =  questionMessageRepository;
    }

    @Override
    public void sendFromMobile(JsonObject body, String session) {
        LOG.info("question id event : " + session);
        if( !QuestionHandlerServiceImpl.moderateurSession.containsKey(session)){
            QuestionHandlerServiceImpl.moderateurSession.put(session,false);
        }
        if( QuestionHandlerServiceImpl.moderateurSession.get(session) == true && body.getString("mode").equals("all")){
            body.remove("mode");
            body.put("mode","moderateur");
            String questionString = body.getString("body");
            JsonObject questionBody = new JsonObject(questionString);
            questionBody.remove("verify");
            questionBody.put("verify",false);
            body.remove("body");
            body.put("body",questionBody);
        }
        eventBus.publish("client/" + PollEnum.QUESTION.toString() + "/" + session, body);
    }

    @Override
    public void sendFromWeb(BridgeEvent event, EventBus eventBus, String session) {
        JsonObject body = event.getRawMessage().getJsonObject("body");
        LOG.info(body.getString("body"));
        if( !QuestionHandlerServiceImpl.moderateurSession.containsKey(session)){
            QuestionHandlerServiceImpl.moderateurSession.put(session,false);
        }
        if( QuestionHandlerServiceImpl.moderateurSession.get(session) == true){
            body.remove("mode");
            body.put("mode","moderateur");
            String questionString = body.getString("body");
            JsonObject questionBody = new JsonObject(questionString);
            questionBody.remove("verify");
            questionBody.put("verify",false);
            body.remove("body");
            body.put("body",questionBody);
        }
        eventBus.publish("client/" + PollEnum.QUESTION.toString() + "/" + session, body);
    }

    @Override
    public void sendModerateur(String session) {
        if (QuestionHandlerServiceImpl.moderateurSession.containsKey(session)) {
            Boolean moderateur = QuestionHandlerServiceImpl.moderateurSession.get(session);
            QuestionHandlerServiceImpl.moderateurSession.put(session,!moderateur);
        }
        else{
            QuestionHandlerServiceImpl.moderateurSession.put(session,true);
        }
        QuestionHandlerServiceImpl.moderateurSession.values().forEach(m -> LOG.info(m));
    }

    @Override
    public void sendFromModerateur(BridgeEvent event, EventBus eventBus, String session) {
        JsonObject body = event.getRawMessage().getJsonObject("body");
        String questionString = body.getString("body");
        JsonObject questionBody = new JsonObject(questionString);
        questionBody.remove("verify");
        questionBody.put("verify",true);
        body.remove("body");
        body.put("body",questionBody);
        eventBus.publish("client/" + PollEnum.QUESTION.toString() + "/" + session, body);
    }

    @Override
    public void sendReponse(BridgeEvent event, EventBus eventBus, String session) {
        JsonObject body = event.getRawMessage().getJsonObject("body");
        LOG.info(body.getString("body"));
        eventBus.publish("client/" + PollEnum.QUESTION.toString() + "/" + session, body);
    }
}
