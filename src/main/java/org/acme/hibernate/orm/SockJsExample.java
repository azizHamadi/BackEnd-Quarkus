package org.acme.hibernate.orm;

import io.vertx.core.eventbus.EventBus;
import io.vertx.core.eventbus.Message;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.bridge.BridgeEventType;
import io.vertx.ext.bridge.PermittedOptions;
import io.vertx.ext.eventbus.bridge.tcp.TcpEventBusBridge;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.StaticHandler;
import io.vertx.ext.web.handler.sockjs.BridgeOptions;
import io.vertx.core.Vertx;
import io.vertx.ext.web.handler.sockjs.SockJSHandler;
import org.acme.hibernate.orm.service.Impl.QuestionHandlerServiceImpl;
import org.acme.hibernate.orm.service.Impl.QuizHandlerServiceImpl;
import org.acme.hibernate.orm.service.Impl.WordCloudHandlerServiceImpl;
import org.acme.hibernate.orm.service.NotificationService;
import org.jboss.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import java.util.*;

@ApplicationScoped
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

    @Inject
    public SockJsExample(NotificationService notificationService, Vertx vertx, QuizHandlerServiceImpl quizHandlerService, QuestionHandlerServiceImpl questionHandlerService, WordCloudHandlerServiceImpl wordCloudHandlerService) {
        this.notificationService = notificationService;
        this.vertx = vertx;
        this.eventBus = vertx.eventBus();
        this.quizHandlerService = quizHandlerService;
        this.questionHandlerService = questionHandlerService;
        this.wordCloudHandlerService = wordCloudHandlerService;
    }

    public void init(@Observes Router router) {
        this.registerQuiz();
        this.registerQuestion();
        this.registerWordCloud();
        TcpEventBusBridge bridge = TcpEventBusBridge.create(
                vertx,
                new io.vertx.ext.bridge.BridgeOptions()
                        .addOutboundPermitted(new PermittedOptions().setAddressRegex("client/" + PollEnum.QUESTION.toString()))
                        .addOutboundPermitted(new PermittedOptions().setAddressRegex("client/" + PollEnum.QUIZ.toString()))
                        .addOutboundPermitted(new PermittedOptions().setAddressRegex("client/" + PollEnum.WORDCLOUD.toString()))
                        .addInboundPermitted(new PermittedOptions().setAddressRegex("server/" + PollEnum.QUESTION.toString()))
                        .addInboundPermitted(new PermittedOptions().setAddressRegex("server/" + PollEnum.QUIZ.toString()))
                        .addInboundPermitted(new PermittedOptions().setAddressRegex("server/" + PollEnum.WORDCLOUD.toString()))
                        );
        bridge.listen(7000, res -> System.out.println("Ready"));
        router.route("/ws/chat/*").handler(eventBusHandler(this.eventBus));
        router.route().handler(StaticHandler.create().setCachingEnabled(false));
    }

    private SockJSHandler eventBusHandler(EventBus eventBus ) {
        BridgeOptions options = new BridgeOptions()
                .addOutboundPermitted(new PermittedOptions().setAddressRegex("client/" + PollEnum.QUESTION.toString()))
                .addOutboundPermitted(new PermittedOptions().setAddressRegex("client/" + PollEnum.QUIZ.toString()))
                .addOutboundPermitted(new PermittedOptions().setAddressRegex("client/" + PollEnum.WORDCLOUD.toString()))
                .addInboundPermitted(new PermittedOptions().setAddressRegex("server/" + PollEnum.QUESTION.toString()))
                .addInboundPermitted(new PermittedOptions().setAddressRegex("server/" + PollEnum.QUIZ.toString()))
                .addInboundPermitted(new PermittedOptions().setAddressRegex("server/" + PollEnum.WORDCLOUD.toString()));
        SockJSHandler sockJSHandler = SockJSHandler.create(vertx);

        sockJSHandler.bridge(options, event -> {
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
        vertx.eventBus().consumer("server/" + PollEnum.QUIZ.toString(), (Message<JsonObject> message) -> {
            JsonObject body = message.body();
            LOG.info(body);
            if(body.getString(PLATEFORME).equals("mobile")){
                if(message.body().containsKey("type")){
                    this.quizHandlerService.register(body);
                }
            }
            else if ( body.getString(PLATEFORME).equals("server")){
                this.quizHandlerService.generateResult(body);
            }
        });
    }

    public void registerQuestion() {
        vertx.eventBus().consumer("server/" + PollEnum.QUESTION.toString(), (Message<JsonObject> message) -> {
            JsonObject body = message.body();
            LOG.info(body);
            if(body.getString(PLATEFORME).equals("mobile")){
                this.questionHandlerService.sendFromMobile(body);
            }
        });
    }

    public void registerWordCloud() {
        vertx.eventBus().consumer("server/" + PollEnum.WORDCLOUD.toString(), (Message<JsonObject> message) -> {
            JsonObject body = message.body();
            LOG.info(body);
            if(body.getString(PLATEFORME).equals("mobile")){
                this.wordCloudHandlerService.sendFromMobile(body);
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