package org.acme.hibernate.orm.web.rest;

import org.acme.hibernate.orm.domain.Feedback;
import org.acme.hibernate.orm.service.Impl.FeedbackServiceImpl;
import org.jboss.logging.Logger;
import org.jboss.resteasy.annotations.jaxrs.PathParam;
import org.jose4j.json.internal.json_simple.JSONObject;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import java.util.List;

@Path("feedbacks")
@ApplicationScoped
@Produces("application/json")
@Consumes("application/json")
public class FeedbackResource {
    private static final Logger LOGGER = Logger.getLogger(FeedbackResource.class.getName());

    @Inject
    private FeedbackServiceImpl feedbackService  ;


    @GET
    public List<Feedback> getAll() {
        return feedbackService.findAll();
    }

    @GET
    @Path("/event/{id}")
    public List<JSONObject> getByEvent(@PathParam int id) {
        List<JSONObject> feedbacks = feedbackService.findByEvent(id);
        return feedbacks;
    }
    @GET
    @Path("/event/web/{id}")
    public List<JSONObject> getByEventW(@PathParam int id) {
        List<JSONObject> feedbacks = feedbackService.findByEventW(id);
        return feedbacks;
    }

    @GET
    @Path("/{id}")
    public JSONObject getFeedbackById(@PathParam int id) {
        JSONObject feedback = feedbackService.getFeedbackById(id);
        return feedback;
    }

    @POST
    public Feedback createFeedback(Feedback feedback) {

        return feedbackService.createFeedback(feedback);
    }




}
