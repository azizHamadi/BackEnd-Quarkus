package org.acme.hibernate.orm.domain;

import io.vertx.core.json.JsonObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Score {
    public Map<Integer,List<JsonObject>> scoreQuizMap;

    public Score() {
        scoreQuizMap = new HashMap<>();
    }

    public Map<Integer, List<JsonObject>> getScoreQuizMap() {
        return scoreQuizMap;
    }

    public void setScoreQuizMap(Map<Integer, List<JsonObject>> scoreQuizMap) {
        this.scoreQuizMap = scoreQuizMap;
    }

    @Override
    public String toString() {
        return "Score{" +
                "scoreQuizMap=" + scoreQuizMap +
                '}';
    }
}
