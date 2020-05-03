package org.acme.hibernate.orm.domain;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "reponseMessage")
@NamedQuery(name = "ReponseMessages.findAll",
        query = "SELECT rM FROM ReponseMessage rM ORDER BY rM.text_reponse",
        hints = @QueryHint(name = "org.hibernate.cacheable", value = "true") )
@Cacheable
public class ReponseMessage implements Serializable {

    @Id
    @SequenceGenerator(
            name = "reponseMessageSequence",
            sequenceName = "known_repMessage_id_seq",
            allocationSize = 1,
            initialValue = 4)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "reponseMessageSequence")
    private Long id_reponseMessage;

    @Column
    private String text_reponse;

    @Column(nullable = false)
    @CreationTimestamp
    private Date date;

    @ManyToOne
    private QuestionMessage questionMessage;

    public ReponseMessage() {
    }

    public ReponseMessage(String text_reponse, QuestionMessage questionMessage){
        this.text_reponse = text_reponse;
        this.questionMessage = questionMessage;
    }

    public ReponseMessage(Long id_reponseMessage, String text_reponse, QuestionMessage questionMessage){
        this.id_reponseMessage = id_reponseMessage;
        this.text_reponse = text_reponse;
        this.questionMessage = questionMessage;
    }

    public Long getId_reponseMessage() {
        return id_reponseMessage;
    }

    public void setId_reponseMessage(Long id_reponseMessage) {
        this.id_reponseMessage = id_reponseMessage;
    }

    public String getText_reponse() {
        return text_reponse;
    }

    public void setText_reponse(String text_reponse) {
        this.text_reponse = text_reponse;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    /*public QuestionMessage getQuestionMessage() {
        return questionMessage;
    }*/

    public void setQuestionMessage(QuestionMessage questionMessage) {
        this.questionMessage = questionMessage;
    }

    @Override
    public String toString() {
        return "ReponseMessage{" +
                "id_reponseMessage=" + id_reponseMessage +
                ", text_reponse='" + text_reponse + '\'' +
                ", date=" + date +
                ", questionMessage=" + questionMessage +
                '}';
    }
}
