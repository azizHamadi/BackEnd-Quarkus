package org.acme.hibernate.orm.repository.WordCloud.Impl;

import org.acme.hibernate.orm.domain.Word;
import org.acme.hibernate.orm.domain.WordCloud;
import org.acme.hibernate.orm.repository.WordCloud.IWordCloudRepository;
import org.jboss.logging.Logger;
import org.jose4j.json.internal.json_simple.JSONObject;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.ws.rs.WebApplicationException;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class WordCloudRepository implements IWordCloudRepository {

    private static final Logger LOG = Logger.getLogger(WordCloudRepository.class);

    @Inject
    EntityManager entityManager;

    @Inject
    WordRepository wordRepository;

    @Override
    public List<JSONObject> findAll() {
        List<WordCloud> wordClouds = entityManager.createNamedQuery("WordCloud.findAll", WordCloud.class)
                .getResultList();
        List<JSONObject> listWordCloud = new ArrayList<>();
        wordClouds.forEach(w -> {
            List<Word> words = wordRepository.getWordbyWordWloud(w.getId());
            JSONObject wordCloudObject = new JSONObject();
            wordCloudObject.put("wordCloud",w);
            wordCloudObject.put("words", words);
            listWordCloud.add(wordCloudObject);
        });
        return listWordCloud;
    }

    @Override
    public WordCloud findWordCloudById(Long id) {
        WordCloud wordCloud = entityManager.find(WordCloud.class, id);
        if (wordCloud == null) {
            throw new WebApplicationException("sondage with id of " + id + " does not exist.", 404);
        }
        return wordCloud;
    }

    @Override
    @Transactional
    public void createWordCloud(WordCloud wordCloud) {
        entityManager.persist(wordCloud);
    }

    @Override
    @Transactional
    public void deleteWordCloud(Long id) {
        WordCloud wordCloud = findWordCloudById(id);
        entityManager.remove(wordCloud);
    }

    @Override
    public List<JSONObject> findByEvent(Long id) {
        List<WordCloud> wordClouds = entityManager.createQuery("select w from WordCloud w " +
                "where w.event = " + id , WordCloud.class).getResultList();
        List<JSONObject> listWordCloud = new ArrayList<>();
        wordClouds.forEach(w -> {
            List<Word> words = wordRepository.getWordbyWordWloud(w.getId());
            JSONObject wordCloudObject = new JSONObject();
            wordCloudObject.put("wordCloud",w);
            wordCloudObject.put("words", words);
            listWordCloud.add(wordCloudObject);
        });
        return listWordCloud;
    }
}
