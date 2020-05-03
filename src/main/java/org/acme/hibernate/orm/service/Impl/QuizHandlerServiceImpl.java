package org.acme.hibernate.orm.service.Impl;

import javax.inject.Singleton;
import javax.inject.Inject;
import javax.json.JsonObjectBuilder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.handler.sockjs.BridgeEvent;
import org.acme.hibernate.orm.PollEnum;
import org.acme.hibernate.orm.domain.Question;
import org.acme.hibernate.orm.domain.Reponse;
import org.acme.hibernate.orm.domain.Score;
import org.acme.hibernate.orm.service.IQuizHandlerService;
import org.jboss.logging.Logger;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Singleton
public class QuizHandlerServiceImpl implements IQuizHandlerService {
    private static final Logger LOG = Logger.getLogger(QuizHandlerServiceImpl.class);

    private final EventBus eventBus;
    private ObjectMapper objectMapper;
    public Map<Integer, List<String>> sessions = new HashMap<>();

    public Map<String, Reponse> reponseQuizMap = new HashMap<>();
    public List<JsonObject> jsonScores = new ArrayList<>();
    public List<Score> scoreList = new ArrayList<>();
    public Map<String,Integer> scoresUser = new HashMap<>();
    public Question question ;

    @Inject
    public QuizHandlerServiceImpl( EventBus eventBus) {
        this.eventBus = eventBus;
        objectMapper = new ObjectMapper();
    }

    @Override
    public void sendQuiz(BridgeEvent event, EventBus eventBus, String session) {
        LOG.info("quiz question : " + session);
        JsonObject jsonObject = event.getRawMessage().getJsonObject("body");
        LOG.info(jsonObject.getString("body"));
        try {
            this.question = objectMapper.readValue(jsonObject.getString("body"), Question.class);
            eventBus.publish("client/" + PollEnum.QUIZ.toString() + "/" + session, jsonObject);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void sendResult(BridgeEvent event, EventBus eventBus, String session) {
        LOG.info("quiz reesult : " + session);
        JsonObject jsonObject = event.getRawMessage().getJsonObject("body");
        LOG.info(jsonObject.getString("body"));
        eventBus.publish("client/" + PollEnum.QUIZ.toString() + "/" + session, jsonObject);
    }

    @Override
    public void sendScore(BridgeEvent event, EventBus eventBus, String session) {
        LOG.info("quiz score : " + session);
        JsonObject jsonObject = event.getRawMessage().getJsonObject("body");
        List<JsonObject> sortedList = jsonScores.stream().sorted((s1,s2) -> Integer.compare(s2.getInteger("score"),s1.getInteger("score")))
                .collect(Collectors.toList());
        jsonObject.put("body",sortedList);
        LOG.info(jsonObject);
        eventBus.publish("client/" + PollEnum.QUIZ.toString() + "/" + session, jsonObject);
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
    public void generateResult(JsonObject body, String session) {
        Integer idEvent = body.getInteger("event");
        LOG.info(body);
        try {
            if(body.containsKey("answers")){
                Reponse reponse = objectMapper.readValue(body.getValue("answers").toString(), Reponse.class);
                String user = body.getValue("user").toString();
                if(reponse.isRep())
                    this.addScore(user,1);
                else
                    this.addScore(user,0);
                int id_reponse = Math.toIntExact(reponse.getId_reponse());
                reponseQuizMap.put(user,reponse);
                Float count = ((float) (reponseQuizMap.values().stream().filter(r ->
                        r.getId_reponse() == id_reponse).count() * 100) / this.sessions.get(idEvent).size());
                this.question.getAnswers().stream().filter(r -> r.getId_reponse() == id_reponse).findFirst().get().setCount(count.longValue());
                this.sendFromMobile(session);
            }
        } catch (JsonProcessingException e) {
            LOG.info(e.getMessage());
        }
    }

    @Override
    public void sendFromMobile(String session) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.put("body",JsonObject.mapFrom(this.question));
        jsonObject.put("plateforme","mobile");
        LOG.info("aaaaaaa " + session);
        LOG.info(scoreList);
        eventBus.publish("client/" + PollEnum.QUIZ.toString() + "/" + session, jsonObject);
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

    @Override
    public void addScore(String user, Integer score){
        OptionalInt index = IntStream.range(0, jsonScores.size())
                .filter(i -> user.equals(jsonScores.get(i).getString("user")))
                .findFirst();
        LOG.info("index = ");
        LOG.info(index);
        if (index.isPresent())
            this.jsonScores.get(index.getAsInt()).put("score",this.jsonScores.get(index.getAsInt()).getInteger("score") + score);
        else
            this.jsonScores.add(new JsonObject().put("user",user).put("score",score));
    }

}
