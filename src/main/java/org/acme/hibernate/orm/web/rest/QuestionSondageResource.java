package org.acme.hibernate.orm.web.rest;

import org.acme.hibernate.orm.domain.QuestionSondage;
import org.acme.hibernate.orm.repository.Sondage.Impl.QuestionSondageRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.logging.Logger;
import org.jboss.resteasy.annotations.jaxrs.PathParam;

@Path("questionSondages")
@ApplicationScoped
@Produces("application/json")
@Consumes("application/json")
public class QuestionSondageResource {

    private static final Logger LOGGER = Logger.getLogger(QuestionSondageResource.class.getName());

    @Inject
    private QuestionSondageRepository questionSondageRepository;

    @GET
    public List<QuestionSondage> getAll() {
        return questionSondageRepository.findAll();
    }

    @POST
    public Response create(QuestionSondage questionSondage) {
        questionSondageRepository.createQuestionSondage(questionSondage);
        return Response.ok(questionSondage).status(201).build();
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam Long id) {
        questionSondageRepository.deleteQuestionSondage(id);
        return Response.status(204).build();
    }

    @GET
    @Path("/sondage/{id}")
    public List<QuestionSondage> getBySondage(@PathParam Long id) {
        List<QuestionSondage> questionSondages = questionSondageRepository.findBySondage(id);
        return questionSondages;
    }

}
