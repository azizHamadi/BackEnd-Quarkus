package org.acme.hibernate.orm.web.rest;

import org.acme.hibernate.orm.domain.Event;
import org.acme.hibernate.orm.domain.Quiz;
import org.acme.hibernate.orm.repository.QuizRepository;
import org.jboss.logging.Logger;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.List;
import org.jboss.resteasy.annotations.jaxrs.PathParam;

@Path("quiz")
@ApplicationScoped
@Produces("application/json")
@Consumes("application/json")
public class QuizResource {
    private static final Logger LOGGER = Logger.getLogger(QuizResource.class.getName());

    @PersistenceContext
    private EntityManager entityManager;

    @Inject
    private QuizRepository quizRepository;

    @GET
    public List<Quiz> getAll() {
        return quizRepository.findAll();
    }

    @POST
    public Response create(Quiz quiz) {
        quizRepository.createQuestion(quiz);
        return Response.ok(quiz).status(201).build();
    }

    @DELETE
    public Response delete(@QueryParam("id") int id) {
        quizRepository.deleteQuestion(id);
        return Response.status(204).build();
    }

    @GET
    @Path("/event/{id}")
    public List<Quiz> getByEvent(@PathParam int id) {
        LOGGER.info(id);
        List<Quiz> quizzes = quizRepository.findByEvent(id);
        return quizzes;
    }
    /*@PUT
    public Response update(Quiz quiz) {
        Quiz quizUploaded = quizRepository.updateQuiz(quiz);
        return Response.ok(quizUploaded).status(204).build();
    }*/

}
