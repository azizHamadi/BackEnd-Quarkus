package org.acme.hibernate.orm.web.rest;

import io.vertx.core.json.JsonObject;
import org.acme.hibernate.orm.domain.Question;
import org.acme.hibernate.orm.domain.QuestionMessage;
import org.acme.hibernate.orm.repository.QuestionMessageRepository;
import org.jboss.logging.Logger;
import org.jboss.resteasy.annotations.jaxrs.PathParam;
import org.jose4j.json.internal.json_simple.JSONObject;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("questionMessages")
@ApplicationScoped
@Produces("application/json")
@Consumes("application/json")
public class QuestionMessageResource {
    private static final Logger LOGGER = Logger.getLogger(EventResource.class.getName());
    @Inject
    QuestionMessageRepository questionMessageRepository;

    @PersistenceContext
    EntityManager entityManager;

    @GET
    public List<QuestionMessage> getAll() {
        return questionMessageRepository.findAll();
    }

    @GET
    @Path("/event/{id}")
    public List<JSONObject> getByEvent(@PathParam Long id) {
        return questionMessageRepository.findByEvent(id);
    }

    @POST
    @Path("/{id}")
    public Response create(QuestionMessage questionMessage,@PathParam Long id) {
        questionMessageRepository.createQuestionMessage(questionMessage, id);
        return Response.ok(questionMessage).status(201).build();
    }

    @DELETE
    @Path("{id_questionMessage}")
    @Transactional
    public Response delete(@PathParam Long id_questionMessage) {
        QuestionMessage entity = entityManager.getReference(QuestionMessage.class, id_questionMessage);
        if (entity == null) {
            throw new WebApplicationException("QuestionMessage with id of " + id_questionMessage + " does not exist.", 404);
        }
        entityManager.remove(entity);
        return Response.status(204).build();
    }

}
