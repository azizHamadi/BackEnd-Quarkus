package org.acme.hibernate.orm.repository.WordCloud;


import org.acme.hibernate.orm.domain.WordCloud;
import org.jose4j.json.internal.json_simple.JSONObject;

import java.util.List;

public interface IWordCloudRepository {
    List<JSONObject> findAll();
    WordCloud findWordCloudById(Long id);
    void createWordCloud(WordCloud wordCloud);
    void deleteWordCloud(Long id);
    List<WordCloud> findByEvent(Long id);
}
