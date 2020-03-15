package org.acme.hibernate.orm;

import io.quarkus.oidc.AccessTokenCredential;
import io.quarkus.security.identity.IdentityProviderManager;
import io.quarkus.security.identity.request.TokenAuthenticationRequest;
import io.quarkus.vertx.http.runtime.security.QuarkusHttpUser;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.eventbus.Message;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.bridge.BridgeEventType;
import io.vertx.ext.bridge.PermittedOptions;
import io.vertx.ext.eventbus.bridge.tcp.TcpEventBusBridge;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.StaticHandler;
import io.vertx.ext.web.handler.impl.HttpStatusException;
import io.vertx.ext.web.handler.sockjs.BridgeEvent;
import io.vertx.ext.web.handler.sockjs.BridgeOptions;
import io.vertx.core.Vertx;
import io.vertx.ext.web.handler.sockjs.SockJSHandler;
import io.vertx.reactivex.ext.auth.User;
import org.jboss.logging.Logger;
import io.vertx.core.Future;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.text.DateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@ApplicationScoped
public class SockJsExample {
    private static final Logger LOG = Logger.getLogger(SockJsExample.class);
    Map<String, Session> sessions = new ConcurrentHashMap<>();

    private IdentityProviderManager identityProviderManager;
    private final NotificationService notificationService;
    private final Vertx vertx;
    private String user ;


    EventBus eventBus;

    @Inject
    public SockJsExample(IdentityProviderManager identityProviderManager, NotificationService notificationService, Vertx vertx) {
        this.identityProviderManager = identityProviderManager;
        this.notificationService = notificationService;
        this.vertx = vertx;
        this.eventBus = vertx.eventBus();
    }
    public void init(@Observes Router router) {
        vertx.eventBus().consumer("chat.to.server", (Message<JsonObject> message) -> {
            LOG.info(message.body());
            if(message.body() instanceof JsonObject){
                vertx.eventBus().publish("chat.to.client", message.body() );
            }
            LOG.info(message.body());
        });

        TcpEventBusBridge bridge = TcpEventBusBridge.create(
                vertx,
                new io.vertx.ext.bridge.BridgeOptions()
                        .addInboundPermitted(new PermittedOptions().setAddress("chat.to.server"))
                        .addOutboundPermitted(new PermittedOptions().setAddress("chat.to.client"))
        );
        bridge.listen(7000, res -> System.out.println("Ready"));

        router.route("/ws/chat/*").handler(eventBusHandler(this.eventBus));
        router.route().handler(StaticHandler.create().setCachingEnabled(false));

    }

    private SockJSHandler eventBusHandler(EventBus eventBus ) {
        BridgeOptions options = new BridgeOptions()
                .addOutboundPermitted(new PermittedOptions().setAddressRegex("chat.to.client"))
                .addInboundPermitted(new PermittedOptions().setAddressRegex("chat.to.server"));

        SockJSHandler sockJSHandler = SockJSHandler.create(vertx);

        sockJSHandler.bridge(options, event -> {
            if (event.type() == BridgeEventType.SOCKET_CREATED) {
                notificationService.onOpen(event,eventBus);
            }

            if (event.type() == BridgeEventType.SEND) {
                LOG.info("send");
                notificationService.onMessage(event,eventBus);
            }

            if (event.type() == BridgeEventType.SOCKET_CLOSED) {
                notificationService.onClose(event,eventBus);
            }

            event.complete(true);
        });
        return sockJSHandler;
    }

}