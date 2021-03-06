package org.acme.hibernate.orm.service.Impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.handler.sockjs.BridgeEvent;
import org.acme.hibernate.orm.PollEnum;
import org.acme.hibernate.orm.domain.Question;
import org.acme.hibernate.orm.service.NotificationService;
import org.jboss.logging.Logger;

import javax.inject.Singleton;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.inject.Inject;


@Singleton
public class NotificationServiceImpl implements NotificationService {

    private static final String PREFERRED_USERNAME = "username";
    private static final String MODEL = "model";
    private Map<String, BridgeEvent> bridgeEvents = new ConcurrentHashMap<>();
    private static final Logger LOG = Logger.getLogger(NotificationService.class);

    private final EventBus eventBus;
    private final QuizHandlerServiceImpl quizHandlerService;
    private final QuestionHandlerServiceImpl questionHandlerService;
    private final WordCloudHandlerServiceImpl wordCloudHandlerService;
    private final SondageHandlerService sondageHandlerService;
    private final FeedbackHandlerServiceImpl feedbackHandlerService ;
    private ObjectMapper objectMapper;
    public Question question;

    @Inject
    public NotificationServiceImpl(EventBus eventBus, QuizHandlerServiceImpl quizHandlerService, QuestionHandlerServiceImpl questionHandlerService, WordCloudHandlerServiceImpl wordCloudHandlerService, SondageHandlerService sondageHandlerService, FeedbackServiceImpl feedbackService, FeedbackHandlerServiceImpl feedbackHandlerService) {
        this.eventBus = eventBus;
        this.quizHandlerService = quizHandlerService;
        this.questionHandlerService = questionHandlerService;
        this.wordCloudHandlerService = wordCloudHandlerService;
        this.sondageHandlerService = sondageHandlerService;
        this.feedbackHandlerService = feedbackHandlerService;
        objectMapper = new ObjectMapper();
    }

    @Override
    public void onOpen(BridgeEvent event, EventBus eventBus) {
        bridgeEvents.put("admin", event);
        event.socket().write(new JsonObject().put("body", "Notification admin joined").toString());
    }

    @Override
    public void onMessage(BridgeEvent event, EventBus eventBus) {
        LOG.info("hhhhhh");
        LOG.info("aaaaa " + event.getRawMessage());

        JsonObject jsonObject = event.getRawMessage();
        JsonObject body = jsonObject.getJsonObject("body");
        LOG.info(body);
        String model = jsonObject.getString("address");
        LOG.info(model);
        String session = body.getInteger("event").toString();
        LOG.info(session);

        if( model.equals( "server/" + PollEnum.QUIZ.toString() )){ /////// quiz handler ///////////
            LOG.info(body.getString("mode"));
            if(body.getString("mode").equals("question")){
                this.quizHandlerService.sendQuiz(event,eventBus,session);
            }
            else if(body.getString("mode").equals("result")){
                this.quizHandlerService.sendResult(event,eventBus,session);
            }
            else{
                this.quizHandlerService.sendScore(event,eventBus,session);
            }
        }
        else if( model.equals("server/" + PollEnum.QUESTION.toString() )){ /////// question handler ///////////
            LOG.info("question : " + session);
            if(body.getString("mode").equals("all") || body.getString("mode").equals("like") || body.getString("mode").equals("dislike")){
                this.questionHandlerService.sendFromWeb(event,eventBus,session);
            }
            else if(body.getString("mode").equals("update")){
                this.questionHandlerService.sendModerateur(session);
            }
            else if(body.getString("mode").equals("verify")){
                this.questionHandlerService.sendFromModerateur(event,eventBus,session);
            }
            else if(body.getString("mode").equals("reponse") || body.getString("mode").equals("delete") || body.getString("mode").equals("order")){
                this.questionHandlerService.sendReponse(event,eventBus,session);
            }
        }
        else if( model.equals("server/" + PollEnum.WORDCLOUD.toString() )){ /////// wordCloud handler ///////////
            this.wordCloudHandlerService.sendFromWeb(event,eventBus,session);
        }
        else if( model.equals("server/" + PollEnum.SONDAGE.toString() )){ /////// wordCloud handler ///////////
            this.sondageHandlerService.sendSondageWeb(event,eventBus,session);
        }
        else if( model.equals("server/" + PollEnum.FEEDBACK.toString() )){ /////// wordCloud handler ///////////
            this.feedbackHandlerService.sendFeedbackWeb(event,eventBus,session);
        }
    }

    @Override
    public void onClose(BridgeEvent event, EventBus eventBus) {
        bridgeEvents.remove("admin");
    }

    private String newMessage(String message) {
        return new JsonObject()
                .put("body", message)
                .put("address", "chat.to.client")
                .toString();
    }

}
