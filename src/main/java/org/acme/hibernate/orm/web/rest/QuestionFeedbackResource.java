package org.acme.hibernate.orm.web.rest;

import org.acme.hibernate.orm.domain.QuestionFeedback;
import org.acme.hibernate.orm.service.Impl.QuestionFeedbackServiceImpl;
import org.jboss.logging.Logger;
import org.jboss.resteasy.annotations.jaxrs.PathParam;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import java.util.List;

@Path("questionFeedbacks")
@ApplicationScoped
@Produces("application/json")
@Consumes("application/json")
public class QuestionFeedbackResource {
    private static final Logger LOGGER = Logger.getLogger(QuestionFeedback.class.getName());

    @Inject
    private QuestionFeedbackServiceImpl questionFeedbackService;


    @GET
    public List<QuestionFeedback> findAll() {
        return questionFeedbackService.findAll();
    }

    @GET
    @Path("feedback/{id}")
    public List<QuestionFeedback> findByFeedback(@PathParam("id")int id) {
        return questionFeedbackService.findByFeedback(id);
    }


    @GET
    @Path("{id}")
    public QuestionFeedback findQuestionFeedbckById(@PathParam("id") int id) {
        return questionFeedbackService.findQuestionFeedbckById(id);
    }

    @POST
    public QuestionFeedback createQuestionFeedback(QuestionFeedback questionFeedback) {
        questionFeedbackService.createQuestionFeedback(questionFeedback);
        return questionFeedback;
    }
}
