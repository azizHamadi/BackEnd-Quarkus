package org.acme.hibernate.orm.domain;

public class Score {
    private String user;
    private Integer score;

    public Score(String user, Integer score) {
        this.user = user;
        this.score = score;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "Score{" +
                "user='" + user + '\'' +
                ", score=" + score +
                '}';
    }
}
