package org.acme.hibernate.orm;

import io.quarkus.vertx.ConsumeEvent;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.eventbus.Message;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.bridge.BridgeEventType;
import io.vertx.ext.eventbus.bridge.tcp.TcpEventBusBridge;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.StaticHandler;
import io.vertx.core.Vertx;
import io.vertx.ext.web.handler.sockjs.SockJSHandler;
import org.acme.hibernate.orm.service.Impl.QuestionHandlerServiceImpl;
import org.acme.hibernate.orm.service.Impl.QuizHandlerServiceImpl;
import org.acme.hibernate.orm.service.Impl.SondageHandlerService;
import org.acme.hibernate.orm.service.Impl.WordCloudHandlerServiceImpl;
import org.acme.hibernate.orm.service.NotificationService;
import org.jboss.logging.Logger;

import javax.enterprise.event.Observes;
import javax.inject.Inject;
import java.util.*;

public class SockJsExample {
    private static final Logger LOG = Logger.getLogger(SockJsExample.class);
    private static final String PLATEFORME = "plateforme";
    private static final String MODE = "all";
    private final NotificationService notificationService;
    private final Vertx vertx;
    private List<String> users = new ArrayList<>();
    EventBus eventBus;
    private final QuizHandlerServiceImpl quizHandlerService;
    private final QuestionHandlerServiceImpl questionHandlerService;
    private final WordCloudHandlerServiceImpl wordCloudHandlerService;
    private final SondageHandlerService sondageHandlerService;
    private String session ;
    private BridgeOptionImpl bridgeOption ;

    @Inject
    public SockJsExample(NotificationService notificationService, Vertx vertx, QuizHandlerServiceImpl quizHandlerService, QuestionHandlerServiceImpl questionHandlerService, WordCloudHandlerServiceImpl wordCloudHandlerService, SondageHandlerService sondageHandlerService, BridgeOptionImpl bridgeOption) {
        this.notificationService = notificationService;
        this.vertx = vertx;
        this.eventBus = vertx.eventBus();
        this.quizHandlerService = quizHandlerService;
        this.questionHandlerService = questionHandlerService;
        this.wordCloudHandlerService = wordCloudHandlerService;
        this.sondageHandlerService = sondageHandlerService;
        this.bridgeOption = bridgeOption;
    }

    @ConsumeEvent(value = "blocking-consumer", blocking = true)
    public void init(@Observes Router router) {
        router.route("/ws/chat/*").handler(routingContext -> {
            session = routingContext.request().getParam("session");
            LOG.info(session);
            this.registerQuiz();
            this.registerQuestion();
            this.registerWordCloud();
            this.registerSondage();
            this.bridgeOption.setBridgeOptionMobile("server/" + PollEnum.QUESTION.toString(),"client/" + PollEnum.QUESTION.toString() + "/" + session);
            this.bridgeOption.setBridgeOptionMobile("server/" + PollEnum.QUIZ.toString(),"client/" + PollEnum.QUIZ.toString() + "/" + session);
            this.bridgeOption.setBridgeOptionMobile("server/" + PollEnum.WORDCLOUD.toString(),"client/" + PollEnum.WORDCLOUD.toString() + "/" + session);
            this.bridgeOption.setBridgeOptionMobile("server/" + PollEnum.SONDAGE.toString(),"client/" + PollEnum.SONDAGE.toString() + "/" + session);
            this.bridgeOption.setBridgeOptionMobile("server/" + PollEnum.FEEDBACK.toString(),"client/" + PollEnum.FEEDBACK.toString() + "/" + session);
            this.bridgeOption.setBridgeOptionWeb("server/" + PollEnum.QUESTION.toString() ,"client/" + PollEnum.QUESTION.toString() + "/" + session);
            this.bridgeOption.setBridgeOptionWeb("server/" + PollEnum.QUIZ.toString() ,"client/" + PollEnum.QUIZ.toString() + "/" + session);
            this.bridgeOption.setBridgeOptionWeb("server/" + PollEnum.WORDCLOUD.toString() ,"client/" + PollEnum.WORDCLOUD.toString() + "/" + session);
            this.bridgeOption.setBridgeOptionWeb("server/" + PollEnum.SONDAGE.toString() ,"client/" + PollEnum.SONDAGE.toString() + "/" + session);
            this.bridgeOption.setBridgeOptionWeb("server/" + PollEnum.FEEDBACK.toString() ,"client/" + PollEnum.FEEDBACK.toString() + "/" + session);
            TcpEventBusBridge bridge = TcpEventBusBridge.create(
                    vertx,
                    this.bridgeOption.getBridgeOptionMobile()
            );
            bridge.listen(7000, res -> System.out.println("Ready"));
            routingContext.next();
        });
        router.route("/ws/chat/*").handler(eventBusHandler(this.eventBus));
        router.route().handler(StaticHandler.create().setCachingEnabled(false));
    }

    private SockJSHandler eventBusHandler(EventBus eventBus ) {
        SockJSHandler sockJSHandler = SockJSHandler.create(vertx);

        sockJSHandler.bridge(this.bridgeOption.getBridgeOptionWeb(), event -> {
            if (event.type() == BridgeEventType.SOCKET_CREATED) {
                notificationService.onOpen(event,eventBus);
            }
            if (event.type() == BridgeEventType.SEND) {
                notificationService.onMessage(event,eventBus);
            }
            if (event.type() == BridgeEventType.SOCKET_CLOSED) {
                notificationService.onClose(event,eventBus);
            }
            event.complete(true);
        });
        return sockJSHandler;
    }

    public void registerQuiz() {
        vertx.eventBus().consumer("server/" + PollEnum.QUIZ.toString() , (Message<JsonObject> message) -> {
            JsonObject body = message.body();
            LOG.info(body);
            String session = body.getInteger("event").toString();
            if(body.getString(PLATEFORME).equals("mobile")){
                if(message.body().containsKey("type")){
                    if(message.body().getString("type").equals("register"))
                    {
                        LOG.info("d5al lel if c bon");
                        this.quizHandlerService.register(body);
                    }
                    else if(message.body().getString("type").equals("logout"))
                        this.quizHandlerService.logout(body);
                }
            }
            else if ( body.getString(PLATEFORME).equals("server")){
                this.quizHandlerService.generateResult(body,session);
            }
        });
    }

    public void registerQuestion() {
        vertx.eventBus().consumer("server/" + PollEnum.QUESTION.toString() , (Message<JsonObject> message) -> {
            JsonObject body = message.body();
            LOG.info(body);
            String session = body.getInteger("event").toString();
            if(body.getString(PLATEFORME).equals("mobile")){
                this.questionHandlerService.sendFromMobile(body,session);
            }
        });
    }

    public void registerWordCloud() {
        vertx.eventBus().consumer("server/" + PollEnum.WORDCLOUD.toString() , (Message<JsonObject> message) -> {
            JsonObject body = message.body();
            LOG.info(body);
            String session = body.getInteger("event").toString();
            if(body.getString(PLATEFORME).equals("mobile")){
                this.wordCloudHandlerService.sendFromMobile(body,session);
            }
        });
    }

    public void registerSondage() {
        vertx.eventBus().consumer("server/" + PollEnum.SONDAGE.toString() , (Message<JsonObject> message) -> {
            JsonObject body = message.body();
            LOG.info(body);
            String session = body.getInteger("event").toString();
            if(body.getString(PLATEFORME).equals("mobile")){
                //sondage methode
            }
            else if ( body.getString(PLATEFORME).equals("server")){
                this.sondageHandlerService.generateResult(body,session);
            }
        });
    }

/*
    private void addSession(String key, String user){
        if (sessions.containsKey(key)) {
            sessions.get(key).add(user);
        }
        else{
            sessions.put(key,addNewUsers(user));
        }
    }
    private List<String> addNewUsers(String user){
        List<String> users = new ArrayList<>();
        users.add(user);
        return users ;
    }*/
}