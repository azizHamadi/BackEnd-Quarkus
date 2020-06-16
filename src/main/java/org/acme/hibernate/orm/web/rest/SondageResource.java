package org.acme.hibernate.orm.web.rest;

import org.acme.hibernate.orm.domain.Sondage;
import org.acme.hibernate.orm.repository.Sondage.Impl.SondageRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.logging.Logger;
import org.jboss.resteasy.annotations.jaxrs.PathParam;
import org.jose4j.json.internal.json_simple.JSONObject;


@Path("sondages")
@ApplicationScoped
@Produces("application/json")
@Consumes("application/json")
public class SondageResource {
    private static final Logger LOGGER = Logger.getLogger(Sondage.class.getName());

    @Inject
    private SondageRepository sondageRepository;

    @GET
    public List<Sondage> getAll() {
        return sondageRepository.findAll();
    }

    @POST
    public Response create(Sondage sondage) {
        sondageRepository.createSondage(sondage);
        return Response.ok(sondage).status(201).build();
    }

    @DELETE
    public Response delete(@QueryParam("id") Long id) {
        sondageRepository.deleteSondage(id);
        return Response.status(204).build();
    }

    @GET
    @Path("/event/{id}")
    public List<JSONObject> getByEvent(@PathParam Long id) {
        List<JSONObject> sondages = sondageRepository.findByEvent(id);
        return sondages;
    }

    /*@PUT
    public Response update(Quiz quiz) {
        Quiz quizUploaded = quizRepository.updateQuiz(quiz);
        return Response.ok(quizUploaded).status(204).build();
    }*/

}
