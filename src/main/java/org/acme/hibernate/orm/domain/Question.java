package org.acme.hibernate.orm.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "question")
@NamedQuery(name = "Questions.findAll",
        query = "SELECT q FROM Question q ORDER BY q.text",
        hints = @QueryHint(name = "org.hibernate.cacheable", value = "true") )
@Cacheable
public class Question implements Serializable {
    @Id
    @SequenceGenerator(
            name = "questionSequence",
            sequenceName = "known_questions_id_seq",
            initialValue = 4)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "questionSequence")
    private Long id_question;

    @Column
    private String text;

    @ManyToOne
    private Quiz quiz;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Reponse> responses=new ArrayList<>();

    public Question(String text, List<Reponse> answers) {
        this.text = text;
        this.responses = answers;
    }

       /*@OneToOne
        private Reponse reponseCorrecte;*/

    public List<Reponse> getAnswers() {
        return responses;
    }

    public void setAnswers(List<Reponse> answers) {
        this.responses = answers;
    }

    public Question() {
    }

    public Long getId_question() {
        return id_question;
    }

    public void setId_question(Long id_question) {
        this.id_question = id_question;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Quiz getQuiz() {
        return quiz;
    }

    public void setQuiz(Quiz quiz) {
        this.quiz = quiz;
    }

    @Override
    public String toString() {
        return "Question{" +
                "id_question=" + id_question +
                ", text='" + text + '\'' +
                ", quiz=" + quiz +
                ", responses=" + responses +
                '}';
    }
}