package org.acme.hibernate.orm.web.rest;

import org.acme.hibernate.orm.domain.Word;
import org.acme.hibernate.orm.repository.WordCloud.Impl.WordRepository;
import org.jboss.logging.Logger;
import org.jboss.resteasy.annotations.jaxrs.PathParam;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("words")
@ApplicationScoped
@Produces("application/json")
@Consumes("application/json")
public class WordResource {

    private static final Logger LOGGER = Logger.getLogger(LikesResource.class.getName());
    @Inject
    WordRepository wordRepository;

    @GET
    @Path("/wordCloud/{id}")
    public List<Word> getByWordCloud(@PathParam Long id) {
        return wordRepository.getWordbyWordWloud(id);
    }

    @POST
    public Response create(Word word) {
        LOGGER.info(word);
        wordRepository.addWord(word);
        return Response.ok(word).status(201).build();
    }

}
