package org.acme.hibernate.orm.web.rest;

import org.acme.hibernate.orm.domain.Aime;
import org.acme.hibernate.orm.domain.QuestionMessage;
import org.acme.hibernate.orm.repository.EventRepository;
import org.acme.hibernate.orm.repository.LikeRepository;
import org.jboss.logging.Logger;
import org.jboss.resteasy.annotations.jaxrs.PathParam;
import org.jose4j.json.internal.json_simple.JSONObject;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("likes")
@ApplicationScoped
@Produces("application/json")
@Consumes("application/json")
public class LikesResource {

    private static final Logger LOGGER = Logger.getLogger(LikesResource.class.getName());
    @Inject
    LikeRepository likeRepository;

    @POST
    @Path("/question/{id}")
    public List<Aime> getByQuestionMessage(QuestionMessage questionMessage, @PathParam Long id) {
        return likeRepository.getLikebyQuestion(questionMessage, id);
    }

    @POST
    @Path("/{id}")
    public Response create(Aime like, @PathParam Long id) {
        LOGGER.info(like);
        likeRepository.addLike(like, id);
        return Response.ok().status(201).build();
    }

    @POST
    @Path("/delete/{id}")
    public Response delete(Aime like, @PathParam Long id) {
        LOGGER.info(like);
        likeRepository.deleteLike(like, id);
        return Response.ok().status(201).build();
    }
}
