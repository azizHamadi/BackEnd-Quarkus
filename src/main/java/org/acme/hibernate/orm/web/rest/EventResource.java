package org.acme.hibernate.orm.web.rest;

import org.acme.hibernate.orm.domain.Event;
import org.acme.hibernate.orm.domain.Notification;
import org.acme.hibernate.orm.repository.EventRepository;
import org.acme.hibernate.orm.repository.Notification.Impl.NotificationRepository;
import org.jboss.logging.Logger;
import org.jboss.resteasy.annotations.jaxrs.PathParam;
import org.jose4j.json.internal.json_simple.JSONObject;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

@Path("events")
@ApplicationScoped
@Produces("application/json")
@Consumes("application/json")
public class EventResource {

    private static final Logger LOGGER = Logger.getLogger(EventResource.class.getName());
    @Inject
    EventRepository eventRepository;

    @Inject
    NotificationRepository notificationRepository;

    @PersistenceContext
    EntityManager entityManager;

    @GET
    public List<Event> getAll() {
        return eventRepository.findAll();
    }

    @POST
    public Response create(Event event) {
        eventRepository.createEvent(event);
        return Response.ok(event).status(201).build();
    }

    @DELETE
    public Response delete(@QueryParam("id") int id) {
        eventRepository.deleteEvent(id);
        return Response.status(204).build();
    }

    @PUT
    public Response update(Event event) {
        Event eventUploaded = eventRepository.updateEvent(event);
        return Response.ok(eventUploaded).status(204).build();
    }

    @GET
    @Path("/getbyStatus/{status}")
    public List<JSONObject> getByStatus(@PathParam String status) {
        LOGGER.info("aaaaaa");
        List<Event> events = entityManager.createQuery("select event from Event event " +
                "where event.status = " + Boolean.parseBoolean(status) + " order by event.startDate" , Event.class).getResultList();
        //events.sort(Comparator.comparing(Event::getStartDate));
        List<JSONObject> eventsListObject = new ArrayList<>();
        events.forEach(e -> {
            List<Notification> notifications = notificationRepository.findByEvent(e.getId());
            JSONObject event = new JSONObject();
            event.put("id",e.getId());
            event.put("name",e.getName());
            event.put("startDate",e.getStartDate());
            event.put("description",e.getDescription());
            event.put("endDate",e.getEndDate());
            event.put("image",e.getImage());
            event.put("status",e.getStatus());
            event.put("type",e.getType());
            event.put("user",e.getUser());
            event.put("notifications",notifications);
            eventsListObject.add(event);
        });
        return eventsListObject ;
        //Event[] events = entityManager.createQuery("select event from Event event " +"where event.status = " + Boolean.parseBoolean(status), Event.class).getResultList().toArray(new Event[0]);
        //return events ;
    }

    @GET
    @Path("/getbyType/{type}")
    public Event[] getByType(@PathParam String type) {
        Event[] events = entityManager.createQuery("select event from Event event " +
                "where event.type = '" + type +  "'", Event.class).getResultList().toArray(new Event[0]);
        return events ;
    }

    @GET
    @Path("/notification/{id}")
    public String subscribeEvent(@PathParam int id){
        return eventRepository.subscribeFirebaseEvent(id);
    }

}
