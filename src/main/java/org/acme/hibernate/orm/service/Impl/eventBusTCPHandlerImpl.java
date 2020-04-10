package org.acme.hibernate.orm.service.Impl;

import io.vertx.core.Vertx;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.eventbus.Message;
import io.vertx.core.json.JsonObject;
import org.acme.hibernate.orm.PollEnum;
import org.acme.hibernate.orm.service.IeventBusTCPHandler;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

public class eventBusTCPHandlerImpl implements IeventBusTCPHandler {

    private final Vertx vertx;
    EventBus eventBus;
    private static final String PLATEFORME = "plateforme";
    private final QuizHandlerServiceImpl quizHandlerService;
    private List<String> users = new ArrayList<>();

    @Inject
    public eventBusTCPHandlerImpl(Vertx vertx, QuizHandlerServiceImpl quizHandlerService) {
        this.vertx = vertx;
        this.eventBus = vertx.eventBus();
        this.quizHandlerService = quizHandlerService;
    }

    @Override
    public void register() {
        this.registerQuestion();
        this.registerQuiz();
    }

    @Override
    public void registerQuestion() {
        /*vertx.eventBus().consumer("server/" + PollEnum.QUESTION.toString(), (Message<JsonObject> message) -> {
            JsonObject body = message.body();
            if(body.getString(PLATEFORME).equals("mobile")){
                if(message.body().containsKey("type")){
                    this.quizHandlerService.register(body);
                }
            }
            else if ( body.getString(PLATEFORME).equals("server")){
                this.quizHandlerService.generateResult(body);
            }
        });*/
    }

    @Override
    public void registerQuiz() {

    }
}
