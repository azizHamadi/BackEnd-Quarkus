package org.acme.hibernate.orm.service.Impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.json.JsonObject;
import org.acme.hibernate.orm.domain.Historique;
import org.acme.hibernate.orm.service.IRegisterHandlerService;
import org.jboss.logging.Logger;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Singleton
public class RegisterHnadlerServiceImpl implements IRegisterHandlerService {

    public static Map<Integer, List<String>> sessions = new HashMap<>();
    public static Map<Integer, Historique> sessionsHistorique = new HashMap<>();
    private static final Logger LOG = Logger.getLogger(RegisterHnadlerServiceImpl.class);

    private final EventBus eventBus;
    private ObjectMapper objectMapper;

    @Inject
    public RegisterHnadlerServiceImpl(EventBus eventBus) {
        this.eventBus = eventBus;
        objectMapper = new ObjectMapper();
    }

    @Override
    public void register(JsonObject body) {
        if(body.getString("type").equals("register")) {
            Integer idEvent = body.getInteger("event");
            LOG.info(idEvent);
            if(!RegisterHnadlerServiceImpl.sessionsHistorique.containsKey(idEvent))
                RegisterHnadlerServiceImpl.sessionsHistorique.put(idEvent, new Historique());
            else{

            }
            this.addSession(idEvent,body.getString("user"));
        }
    }

    @Override
    public void addSession(Integer key, String user) {
        if (RegisterHnadlerServiceImpl.sessions.containsKey(key)) {
            RegisterHnadlerServiceImpl.sessions.get(key).add(user);
        }
        else{
            RegisterHnadlerServiceImpl.sessions.put(key,addNewUsers(user));
        }
    }

    @Override
    public List<String> addNewUsers(String user) {
        List<String> users = new ArrayList<>();
        users.add(user);
        return users ;
    }
}
