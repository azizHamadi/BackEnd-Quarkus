package org.acme.hibernate.orm.web.rest;

import org.acme.hibernate.orm.domain.ReponseFeedBackUser;
import org.acme.hibernate.orm.domain.ReponseFeedback;
import org.acme.hibernate.orm.service.Impl.ReponseFeedBackUserServiceImpl;
import org.jboss.logging.Logger;
import org.jboss.resteasy.annotations.jaxrs.PathParam;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import java.util.List;

@Path("reponseFeedbacksUser")
@ApplicationScoped
@Produces("application/json")
@Consumes("application/json")
public class ReponseFeedBackUserResource {
    private static final Logger LOGGER = Logger.getLogger(ReponseFeedBackUserResource.class.getName());

    @Inject
    ReponseFeedBackUserServiceImpl reponseFeedBackUserService;

    @GET
    public List<ReponseFeedBackUser> findAll() {
        return reponseFeedBackUserService.findAll();
    }

    @GET
    @Path("{id}")
    public ReponseFeedBackUser findSingle(@PathParam("id") int id) {
        return reponseFeedBackUserService.findSingle(id);
    }

    @POST
    public ReponseFeedBackUser create(ReponseFeedBackUser reponseFeedBackUser) {
        reponseFeedBackUserService.create(reponseFeedBackUser);
        return reponseFeedBackUser;
    }
}
