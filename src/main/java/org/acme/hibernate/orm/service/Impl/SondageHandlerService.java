package org.acme.hibernate.orm.service.Impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.handler.sockjs.BridgeEvent;
import org.acme.hibernate.orm.PollEnum;
import org.acme.hibernate.orm.domain.*;
import org.acme.hibernate.orm.service.ISondageHandlerService;
import org.jboss.logging.Logger;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Singleton
public class SondageHandlerService implements ISondageHandlerService {

    private static final Logger LOG = Logger.getLogger(SondageHandlerService.class);

    private final EventBus eventBus;
    private ObjectMapper objectMapper;
    public Map<ReponseQuestionSondage, List<String>> reponseQuestionSondageMap = new HashMap<>();

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
        Integer idEvent = Integer.parseInt(session);
        try {
            ReponseQuestionSondage reponse = objectMapper.readValue(body.getValue("body").toString(), ReponseQuestionSondage.class);
            QuestionSondage quesiton = objectMapper.readValue(body.getValue("question").toString(), QuestionSondage.class);
            String user = body.getValue("user").toString();
            if ( !reponseQuestionSondageMap.containsKey(reponse))
                reponseQuestionSondageMap.put(reponse,new ArrayList<>());
            reponseQuestionSondageMap.get(reponse).add(user);
            LOG.info(QuizHandlerServiceImpl.sessions.size());
            Float count = ((float) (reponseQuestionSondageMap.get(reponse).size() * 100) / QuizHandlerServiceImpl.sessions.get(idEvent).size());
            this.sendFromMobile(quesiton,reponse,count,session);
        } catch (JsonProcessingException e) {
            LOG.info(e.getMessage());
        }
    }

    @Override
    public void sendFromMobile(QuestionSondage question, ReponseQuestionSondage reponseQuestionSondage, Float count, String session) {
        JsonObject jsonObject = new JsonObject();
        LOG.info(question);
        jsonObject.put("body",JsonObject.mapFrom(reponseQuestionSondage));
        jsonObject.put("question",JsonObject.mapFrom(question));
        jsonObject.put("count",count);
        jsonObject.put("mode","reponse");
        jsonObject.put("plateforme","mobile");
        LOG.info("reponse sondage ");
        LOG.info(jsonObject);
        eventBus.publish("client/" + PollEnum.SONDAGE.toString() + "/" + session, jsonObject);
    }

    @Override
    public void sendSondageWeb(BridgeEvent event, EventBus eventBus, String session) {
        LOG.info("sondage : " + session);
        JsonObject jsonObject = event.getRawMessage().getJsonObject("body");
        LOG.info(jsonObject.getString("body"));
        eventBus.publish("client/" + PollEnum.SONDAGE.toString() + "/" + session, jsonObject);
    }
}
