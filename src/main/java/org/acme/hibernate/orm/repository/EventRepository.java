package org.acme.hibernate.orm.repository;

import org.acme.hibernate.orm.domain.Event;
import org.jboss.resteasy.annotations.jaxrs.PathParam;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.WebApplicationException;
import java.util.List;


@ApplicationScoped
public class EventRepository {
    @Inject
    EntityManager entityManager;

    public List<Event> findAll() {
        return entityManager.createNamedQuery("Events.findAll", Event.class)
                .getResultList();
    }

    public Event findEventById(int id) {
        Event event = entityManager.find(Event.class, id);
        if (event == null) {
            throw new WebApplicationException("Event with id of " + id + " does not exist.", 404);
        }
        return event;
    }

    @Transactional
    public Event updateEvent(Event event) {
        Event eventToUpdate = findEventById(event.getId());
        eventToUpdate.setDescription(event.getDescription());
        eventToUpdate.setName(event.getName());
        eventToUpdate.setEndDate(event.getEndDate());
        eventToUpdate.setStartDate(event.getStartDate());
        eventToUpdate.setImage(event.getImage());
        eventToUpdate.setStatus(event.getStatus());
        eventToUpdate.setType(event.getType());
        entityManager.flush();
        return eventToUpdate;
    }

    @Transactional
    public void createEvent(Event event) {
      entityManager.persist(event);
    }

    @Transactional
    public void deleteEvent(int id) {
        Event c = findEventById(id);
        entityManager.remove(c);
    }

    @GET
    @Path("/getbyStatus/{status}")
    public Event[] getByStatus(@PathParam String status) {
        Event[] events = entityManager.createQuery("select event from Event event " +
                "where event.status = " + status , Event.class).getResultList().toArray(new Event[0]);
        return events ;
    }

    @GET
    @Path("/getbyType/{type}")
    public Event[] getByType(@PathParam String type) {
        Event[] events = entityManager.createQuery("select event from Event event " +
                "where event.type = '" + type +  "'", Event.class).getResultList().toArray(new Event[0]);
        return events ;
    }

}
