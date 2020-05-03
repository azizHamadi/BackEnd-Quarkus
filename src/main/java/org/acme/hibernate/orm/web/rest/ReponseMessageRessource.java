package org.acme.hibernate.orm.web.rest;

import org.acme.hibernate.orm.domain.Quiz;
import org.acme.hibernate.orm.domain.ReponseMessage;
import org.acme.hibernate.orm.repository.QuizRepository;
import org.acme.hibernate.orm.repository.ReponseMessageRepository;
import org.jboss.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("reponseMessages")
@ApplicationScoped
@Produces("application/json")
@Consumes("application/json")
public class ReponseMessageRessource {
    private static final Logger LOGGER = Logger.getLogger(ReponseMessageRessource.class.getName());

    @Inject
    private ReponseMessageRepository reponseMessageRepository;

    @GET
    public List<ReponseMessage> getAll() {
        return reponseMessageRepository.findAll();
    }

    @POST
    @Transactional
    public Response create(ReponseMessage reponseMessage) {
        reponseMessageRepository.createReponseMessage(reponseMessage);
        return Response.ok(reponseMessage).status(201).build();
    }

    @DELETE
    @Transactional
    public Response delete(@QueryParam("id") int id) {
        reponseMessageRepository.deleteReponseMessage(id);
        return Response.status(204).build();
    }

    @GET
    @Path("/event/{id}")
    public List<ReponseMessage> getByEvent(@org.jboss.resteasy.annotations.jaxrs.PathParam int id) {
        return reponseMessageRepository.findByEvent(id);
    }

}
