package org.acme.hibernate.orm.web.rest;


import io.vertx.core.Vertx;
import org.acme.hibernate.orm.domain.Question;
import org.acme.hibernate.orm.domain.Reponse;
import org.acme.hibernate.orm.service.Impl.QuestionServiceImpl;
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
import java.util.List;


@Path("questions")
@ApplicationScoped
@Produces("application/json")
@Consumes("application/json")
public class QuestionResource {
    private static final Logger LOGGER = Logger.getLogger(QuestionResource.class.getName());

    @PersistenceContext
    EntityManager entityManager;

    @Inject
    QuestionServiceImpl questionService;

    @GET
    public Question[] get() {
        return entityManager.createNamedQuery("Questions.findAll", Question.class)
                .getResultList().toArray(new Question[0]);
    }

    @GET
    @Path("listquestion/{id}")
    public List<Reponse> getResponsesByQuestion(@PathParam Long id) {
        Question entity = entityManager.find(Question.class, id);
        if (entity == null) {
            throw new WebApplicationException("Question with id of " + id + " does not exist.", 404);
        }
        return entity.getAnswers();
    }

    @POST
    @Transactional
    public Response create(Question question) {
        if (question.getId_question() != null) {
            throw new WebApplicationException("Id was invalidly set on request.", 422);
        }
        questionService.createQuestion(question);
        return Response.ok(question).status(201).build();
    }

    @PUT
    @Path("{id_question}")
    @Transactional
    public Question update(@PathParam Long id_question, Question question) {
        if (question.getText() == null) {
            throw new WebApplicationException("Fruit Name was not set on request.", 422);
        }
        Question entity = entityManager.find(Question.class, id_question);
        if (entity == null) {
            throw new WebApplicationException("Question with id of " + id_question + " does not exist.", 404);
        }
        entity.setText(question.getText());
        return entity;
    }

    @DELETE
    @Path("{id_question}")
    @Transactional
    public Response delete(@PathParam Long id_question) {
        Question entity = entityManager.getReference(Question.class, id_question);
        if (entity == null) {
            throw new WebApplicationException("Question with id of " + id_question + " does not exist.", 404);
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