package org.acme.hibernate.orm.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "questionSondage")
@NamedQuery(name = "QuestionSondage.findAll",
        query = "SELECT qs FROM QuestionSondage qs ORDER BY qs.text",
        hints = @QueryHint(name = "org.hibernate.cacheable", value = "true") )
@Cacheable
public class QuestionSondage {

    @Id
    @SequenceGenerator(
            name = "questionSondageSequence",
            sequenceName = "questionsSondage_id_seq",
            initialValue = 4)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "questionSondageSequence")
    private Long id;

    @Column
    private String text;

    @ManyToOne
    private Sondage sondage;

    @ManyToOne
    private UserDTO user;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ReponseQuestionSondage> responses=new ArrayList<>();

    public QuestionSondage(String text, List<ReponseQuestionSondage> answers) {
        this.text = text;
        this.responses = answers;
    }

    public QuestionSondage(){}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    /*public Sondage getSondage() {
        return sondage;
    }*/

    public void setSondage(Sondage sondage) {
        this.sondage = sondage;
    }

    public List<ReponseQuestionSondage> getResponses() {
        return responses;
    }

    public void setResponses(List<ReponseQuestionSondage> responses) {
        this.responses = responses;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "QuestionSondage{" +
                "id=" + id +
                ", text='" + text + '\'' +
                ", sondage=" + sondage +
                ", user=" + user +
                ", responses=" + responses +
                '}';
    }
}
