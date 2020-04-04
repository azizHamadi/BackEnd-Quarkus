package org.acme.hibernate.orm.web.rest;

import io.vertx.core.Vertx;
import org.acme.hibernate.orm.domain.Reponse;
import org.acme.hibernate.orm.service.ReponseService;
import org.jboss.logging.Logger;
import org.jboss.resteasy.annotations.jaxrs.PathParam;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonObjectBuilder;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Path("reponses")
@ApplicationScoped
@Produces("application/json")
@Consumes("application/json")
public class ReponseResource {
    private static final Logger LOGGER = Logger.getLogger(ReponseResource.class.getName());
    @Inject
    Vertx vertx;

    @PersistenceContext
    EntityManager entityManager;

    ReponseService reponseService;

    @GET
    public Reponse[] get() {
        return entityManager.createNamedQuery("Reponses.findAll", Reponse.class)
                .getResultList().toArray(new Reponse[0]);
    }

    @GET
    @Path("{id}")
    public Reponse getSingle(@PathParam Long id) {
        Reponse entity = entityManager.find(Reponse.class, id);
        if (entity == null) {
            throw new WebApplicationException("Reponse with id of " + id + " does not exist.", 404);
        }
        return entity;
    }
    @POST
    @Transactional
    public Response create(Reponse reponse) {
        if (reponse.getId_reponse() != null) {
            throw new WebApplicationException("Id was invalidly set on request.", 422);
        }

        entityManager.persist(reponse);
        return Response.ok(reponse).status(201).build();
    }


    @PUT
    @Path("{id}")
    public Reponse update(@PathParam Long id, Reponse reponse) {

       return reponseService.update(id,reponse);




       /* if (reponse.getText_reponse() == null) {
            throw new WebApplicationException("Reponse Name was not set on request.", 422);
        }

        Reponse entity = entityManager.find(Reponse.class, id);

        if (entity == null) {
            throw new WebApplicationException("Reponse with id of " + id + " does not exist.", 404);
        }

        //entity.setText_reponse(reponse.getText_reponse());

        entity.setCount(entity.getCount()+1);

        return entity;*/

    }

    @DELETE
    @Path("{id}")
    public Response delete(@PathParam Long id) {
        Reponse entity = entityManager.getReference(Reponse.class, id);
        if (entity == null) {
            throw new WebApplicationException("Fruit with id of " + id + " does not exist.", 404);
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