package org.acme.hibernate.orm;

import io.vertx.core.Vertx;
import org.jboss.logging.Logger;
import org.jboss.resteasy.annotations.jaxrs.PathParam;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonObjectBuilder;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Path("events")
@ApplicationScoped
@Produces("application/json")
@Consumes("application/json")
public class EventResource {

    private static final Logger LOGGER = Logger.getLogger(EventResource.class.getName());

    @Inject
    Vertx vertx;

    @Inject
    EntityManager entityManager;

    @GET
    public Event[] get() {
        return entityManager.createNamedQuery("Events.findAll", Event.class)
                .getResultList().toArray(new Event[0]);
    }

    @GET
    @Path("/getbyStatus/{status}")
    public Event[] getByStatus(@PathParam String status) {
        LOGGER.info("aaaaaa");
        Event[] events = entityManager.createQuery("select event from Event event " +
                "where event.status = '" + status +  "'", Event.class).getResultList().toArray(new Event[0]);
        return events ;
    }

    @GET
    @Path("/getbyType/{type}")
    public Event[] getByType(@PathParam String type) {
        LOGGER.info("aaaaaa");
        Event[] events = entityManager.createQuery("select event from Event event " +
                "where event.type = '" + type +  "'", Event.class).getResultList().toArray(new Event[0]);
        return events ;
    }

    @GET
    @Path("/{id}")
    public Event getSingle(@PathParam Integer id) {
        Event entity = entityManager.find(Event.class, id);
        if (entity == null) {
            throw new WebApplicationException("Event with id of " + id + " does not exist.", 404);
        }
        return entity;
    }

    @POST
    @Transactional
    public Response create(Event event) {
        if (event.getId() != null) {
            throw new WebApplicationException("Id was invalidly set on request.", 422);
        }
        LOGGER.info("d5al lel create " + event.getName());
        entityManager.persist(event);
        return Response.ok(event).status(201).build();
    }

    @PUT
    @Path("{id}")
    @Transactional
    public Event update(@PathParam Integer id, Event event) {
        if (event.getName() == null) {
            throw new WebApplicationException("Event Name was not set on request.", 422);
        }

        Event entity = entityManager.find(Event.class, id);

        if (entity == null) {
            throw new WebApplicationException("Event with id of " + id + " does not exist.", 404);
        }

        entity.setName(event.getName());
        return entity;
    }

    @DELETE
    @Path("{id}")
    @Transactional
    public Response delete(@PathParam Integer id) {
        Event entity = entityManager.getReference(Event.class, id);
        if (entity == null) {
            throw new WebApplicationException("Event with id of " + id + " does not exist.", 404);
        }
        entityManager.remove(entity);
        return Response.status(204).build();
    }

    @Provider
    public static class ErrorMapper implements ExceptionMapper<Exception> {

        @Override
        public Response toResponse(Exception exception) {
            LOGGER.error("Failed to handle request", exception);

            int code = 500;
            if (exception instanceof WebApplicationException) {
                code = ((WebApplicationException) exception).getResponse().getStatus();
            }

            JsonObjectBuilder entityBuilder = Json.createObjectBuilder()
                    .add("exceptionType", exception.getClass().getName())
                    .add("code", code);

            if (exception.getMessage() != null) {
                entityBuilder.add("error", exception.getMessage());
            }

            return Response.status(code)
                    .entity(entityBuilder.build())
                    .build();
        }

    }
}
