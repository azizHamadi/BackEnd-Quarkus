package org.acme.hibernate.orm.domain;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "questionMessage")
@NamedQuery(name = "QuestionMessages.findAll",
        query = "SELECT qM FROM QuestionMessage qM ORDER BY qM.text_message",
        hints = @QueryHint(name = "org.hibernate.cacheable", value = "true") )
@Cacheable
public class QuestionMessage implements Serializable {

    @Id
    @SequenceGenerator(
            name = "questionMessageSequence",
            sequenceName = "known_questionMessages_id_seq",
            initialValue = 4)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "questionMessageSequence")
    private Long id_questionMessage;

    @Column
    private String text_message;

    @Column
    private String color;

    @Column
    private String userName;

    @Column
    private Boolean verify;

    @Column(nullable = false)
    @CreationTimestamp
    private Date date;

    @ManyToOne
    private Event event;

    public QuestionMessage() {
    }

    public QuestionMessage(String text_message, String color, String userName, Boolean verify){
        this.text_message = text_message;
        this.color = color;
        this.userName = userName;
        this.verify = verify;
    }

    public QuestionMessage(Long id_questionMessage, String text_message, String color, String userName, Boolean verify){
        this.id_questionMessage = id_questionMessage;
        this.text_message = text_message;
        this.color = color;
        this.userName = userName;
        this.verify = verify;
    }

    public Long getId_questionMessage() {
        return id_questionMessage;
    }

    public void setId_questionMessage(Long id_questionMessage) {
        this.id_questionMessage = id_questionMessage;
    }

    public String getText_message() {
        return text_message;
    }

    public void setText_message(String text_message) {
        this.text_message = text_message;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public Boolean getVerify() {
        return verify;
    }

    public void setVerify(Boolean verify) {
        this.verify = verify;
    }

    @Override
    public String toString() {
        return "QuestionMessage{" +
                "id_questionMessage=" + id_questionMessage +
                ", text_message='" + text_message + '\'' +
                ", color='" + color + '\'' +
                ", userName='" + userName + '\'' +
                ", verify=" + verify +
                ", date=" + date +
                '}';
    }
}
