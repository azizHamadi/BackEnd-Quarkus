package org.acme.hibernate.orm.service.Impl;

import javax.inject.Singleton;
import javax.inject.Inject;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.handler.sockjs.BridgeEvent;
import org.acme.hibernate.orm.PollEnum;
import org.acme.hibernate.orm.domain.Question;
import org.acme.hibernate.orm.domain.Reponse;
import org.acme.hibernate.orm.service.IQuizHandlerService;
import org.jboss.logging.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Singleton
public class QuizHandlerServiceImpl implements IQuizHandlerService {
    private static final Logger LOG = Logger.getLogger(QuizHandlerServiceImpl.class);

    private final EventBus eventBus;
    private ObjectMapper objectMapper;
    public Map<Integer, List<String>> sessions = new java.util.HashMap<>();

    public Map<String, Reponse> reponseQuizMap = new java.util.HashMap<>();
    public Question question ;

    @Inject
    public QuizHandlerServiceImpl( EventBus eventBus) {
        this.eventBus = eventBus;
        objectMapper = new ObjectMapper();
    }

    @Override
    public void sendQuiz(BridgeEvent event, EventBus eventBus) {
        JsonObject jsonObject = event.getRawMessage().getJsonObject("body");
        LOG.info(jsonObject.getString("body"));
        try {
            this.question = objectMapper.readValue(jsonObject.getString("body"), Question.class);
            eventBus.publish("client/" + PollEnum.QUIZ.toString(), jsonObject);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void register(JsonObject body) {
        if(body.getString("type").equals("register")) {
            Integer idEvent = body.getInteger("event");
            LOG.info(idEvent);
            this.addSession(idEvent,body.getString("user"));
        }
    }

    @Override
    public void generateResult(JsonObject body) {
        Integer idEvent = body.getInteger("event");
        try {
            if(body.containsKey("answers")){
                Reponse reponse = objectMapper.readValue(body.getValue("answers").toString(), Reponse.class);
                String user = body.getValue("user").toString();
                int id_reponse = Math.toIntExact(reponse.getId_reponse());
                reponseQuizMap.put(user,reponse);
                Float count = ((float) (reponseQuizMap.values().stream().filter(r ->
                        r.getId_reponse() == id_reponse).count() * 100) / this.sessions.get(idEvent).size());
                this.question.getAnswers().stream().filter(r -> r.getId_reponse() == id_reponse).findFirst().get().setCount(count.longValue());
                this.sendFromMobile();
            }
        } catch (JsonProcessingException e) {
            LOG.info(e.getMessage());
        }
    }

    @Override
    public void sendFromMobile() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.put("body",JsonObject.mapFrom(this.question));
        jsonObject.put("plateforme","mobile");
        eventBus.publish("client/" + PollEnum.QUIZ.toString(), jsonObject);
    }

    @Override
    public void addSession(Integer key, String user){
        if (sessions.containsKey(key)) {
            sessions.get(key).add(user);
        }
        else{
            sessions.put(key,addNewUsers(user));
        }
    }

    @Override
    public List<String> addNewUsers(String user){
        List<String> users = new ArrayList<>();
        users.add(user);
        return users ;
    }

}
