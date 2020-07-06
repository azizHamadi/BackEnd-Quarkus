package org.acme.hibernate.orm.web.rest;

import org.acme.hibernate.orm.domain.ReponseQuestionSondage;
import org.acme.hibernate.orm.repository.Sondage.Impl.ReponseQuestionSondageRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.logging.Logger;

@Path("reponseQuestionSondages")
@ApplicationScoped
@Produces("application/json")
@Consumes("application/json")
public class ReponseQuestionSondageResource {

    private static final Logger LOGGER = Logger.getLogger(ReponseQuestionSondageResource.class.getName());

    @Inject
    private ReponseQuestionSondageRepository reponseQuestionSondageRepository;

    @GET
    public List<ReponseQuestionSondage> getAll() {
        return reponseQuestionSondageRepository.findAll();
    }

    @POST
    public Response create(ReponseQuestionSondage reponseQuestionSondage) {
        reponseQuestionSondageRepository.createReponseQuestionSondage(reponseQuestionSondage);
        return Response.ok(reponseQuestionSondage).status(201).build();
    }

    @DELETE
    public Response delete(@QueryParam("id") Long id) {
        reponseQuestionSondageRepository.deleteReponseQuestionSondage(id);
        return Response.status(204).build();
    }

    @PUT
    public Response update(@QueryParam("id") Long id, @QueryParam("count") Double count) {
        reponseQuestionSondageRepository.updateReponseQuestionSondage(id,count);
        return Response.status(204).build();
    }

}
