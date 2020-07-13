package org.acme.hibernate.orm.web.rest;

import org.acme.hibernate.orm.domain.ReponseFeedback;
import org.acme.hibernate.orm.service.Impl.ReponseFeedbackServiceImpl;
import org.jboss.logging.Logger;
import org.jboss.resteasy.annotations.jaxrs.PathParam;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import java.util.List;

@Path("reponseFeedbacks")
@ApplicationScoped
@Produces("application/json")
@Consumes("application/json")
public class ReponseFeedbackResource {
    private static final Logger LOGGER = Logger.getLogger(ReponseFeedback.class.getName());

    @Inject
    private ReponseFeedbackServiceImpl reponseFeedbackService;

    @GET
    public List<ReponseFeedback> findAll() {
        return reponseFeedbackService.findAll();
    }

    @GET
    @Path("{id}")
    public ReponseFeedback findSingle(@PathParam("id") int id) {
        return reponseFeedbackService.findSingle(id);
    }

    @POST
    public void create(ReponseFeedback reponseFeedback) {
        reponseFeedbackService.create(reponseFeedback);
    }

}