package org.acme.hibernate.orm.domain;

import java.util.ArrayList;
import java.util.List;

public class Historique {

    public String pollType;
    public String mode = "historique";
    public List<QuestionMessage> questionMessages;
    public Question question;

    public Historique() {
        this.questionMessages = new ArrayList<>();
    }

    public String getPollType() {
        return pollType;
    }

    public void setPollType(String pollType) {
        this.pollType = pollType;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public List<QuestionMessage> getQuestionMessages() {
        return questionMessages;
    }

    public void setQuestionMessages(QuestionMessage questionMessage) {
        this.questionMessages.add(questionMessage);
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    @Override
    public String toString() {
        return "Historique{" +
                "pollType='" + pollType + '\'' +
                ", mode='" + mode + '\'' +
                ", questionMessages=" + questionMessages +
                ", question=" + question +
                '}';
    }
}
