package org.acme.hibernate.orm.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "QuestionFeedback")
@NamedQuery(name = "QuestionsFeedback.findAll",
        query = "SELECT qf FROM QuestionFeedback qf ORDER BY qf.text",
        hints = @QueryHint(name = "org.hibernate.cacheable", value = "true") )
@Cacheable
public class QuestionFeedback implements Serializable {
    @Id
    @SequenceGenerator(
            name = "QuestionFeedback",
            sequenceName = "known_QuestionsFeedback_id_seq",
            initialValue = 4)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "QuestionFeedback")
    private int id;

    @Column
    private String text;

    @Column
    private String type;

    @ManyToOne
    private Feedback feedback;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<ReponseFeedback> responses;

    @OneToMany(mappedBy = "questionFeedback")
    List<ReponseFeedBackUser> reponseFeedBackUsers;

    public QuestionFeedback() {

    }

    public QuestionFeedback(String text, String type, Feedback feedback, List<ReponseFeedback> responses) {
        this.text = text;
        this.type = type;
        this.feedback = feedback;
        this.responses = responses;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<ReponseFeedback> getResponses() {
        return responses;
    }

    public void setResponses(List<ReponseFeedback> responses) {
        this.responses = responses;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    /*public Feedback getFeedback() {
        return feedback;
    }*/

    public void setFeedback(Feedback feedback) {
        this.feedback = feedback;
    }


    @Override
    public String toString() {
        return "QuestionFeedback{" +
                "id=" + id +
                ", text='" + text + '\'' +
                ", type='" + type + '\'' +
                ", feedback=" + feedback +
                ", responses=" + responses +
                '}';
    }
}