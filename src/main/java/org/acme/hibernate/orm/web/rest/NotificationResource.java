package org.acme.hibernate.orm.web.rest;

import org.acme.hibernate.orm.domain.Notification;
import org.acme.hibernate.orm.service.Impl.NotificationFeedbackService;
import org.jboss.resteasy.annotations.jaxrs.PathParam;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("notifications")
@ApplicationScoped
@Produces("application/json")
@Consumes("application/json")
public class NotificationResource {

    @Inject
    NotificationFeedbackService notificationFeedbackService;

    @POST
    @Path("")
    public Response createNotification(Notification notification) {
        notificationFeedbackService.createNotification(notification);
        return Response.ok(notification).status(201).build();
    }

    @PUT
    @Path("/{id}")
    public Response updateNotification(Notification notification, @PathParam int id){
        notificationFeedbackService.updateNotification(notification, id);
        return Response.ok(notification).status(201).build();
    }

    @GET
    @Path("/findByEvent/{id}")
    public List<Notification> findByEvent(@PathParam int id){
        return notificationFeedbackService.findByEvent(id);
    }

    @GET
    @Path("/findByUser/{id}")
    public List<Notification> findByUser(@PathParam String id){
        return notificationFeedbackService.findByUser(id);
    }
}
