package org.acme.hibernate.orm.web.rest;


import org.acme.hibernate.orm.domain.Sondage;
import org.acme.hibernate.orm.domain.WordCloud;
import org.acme.hibernate.orm.repository.WordCloud.Impl.WordCloudRepository;
import org.jboss.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import org.jboss.resteasy.annotations.jaxrs.PathParam;
import org.jose4j.json.internal.json_simple.JSONObject;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("wordClouds")
@ApplicationScoped
@Produces("application/json")
@Consumes("application/json")
public class WordCloudResource {

    private static final Logger LOGGER = Logger.getLogger(WordCloudResource.class.getName());

    @Inject
    WordCloudRepository wordCloudRepository;

    @GET
    public List<JSONObject> getAll() {
        return wordCloudRepository.findAll();
    }

    @POST
    public Response create(WordCloud wordCloud) {
        wordCloudRepository.createWordCloud(wordCloud);
        return Response.ok(wordCloud).status(201).build();
    }

    @DELETE
    public Response delete(@QueryParam("id") Long id) {
        wordCloudRepository.deleteWordCloud(id);
        return Response.status(204).build();
    }

    @GET
    @Path("/event/{id}")
    public List<WordCloud> getByEvent(@PathParam Long id) {
        List<WordCloud> wordClouds = wordCloudRepository.findByEvent(id);
        return wordClouds;
    }
}
