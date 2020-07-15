package org.acme.hibernate.orm.domain;
import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "reponseFeedBackUser")
@NamedQuery(name = "reponseFeedBackUser.findAll",
        query = "SELECT r FROM ReponseFeedBackUser r",
        hints = @QueryHint(name = "org.hibernate.cacheable", value = "true") )
@Cacheable
public class ReponseFeedBackUser implements Serializable {
    @Id
    @ManyToOne
    UserDTO userDTO;

    @Id
    @ManyToOne
    QuestionFeedback questionFeedback;

    @ManyToOne
    ReponseFeedback reponseFeedback;

    @Column(nullable = true)
    String comment;

    public UserDTO getUserDTO() {
        return userDTO;
    }

    public void setUserDTO(UserDTO userDTO) {
        this.userDTO = userDTO;
    }

    public QuestionFeedback getQuestionFeedback() {
        return questionFeedback;
    }

    public void setQuestionFeedback(QuestionFeedback questionFeedback) {
        this.questionFeedback = questionFeedback;
    }

    public ReponseFeedback getReponseFeedback() {
        return reponseFeedback;
    }

    public void setReponseFeedback(ReponseFeedback reponseFeedback) {
        this.reponseFeedback = reponseFeedback;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public String toString() {
        return "ReponseFeedBackUser{" +
                "userDTO=" + userDTO +
                ", questionFeedback=" + questionFeedback +
                ", reponseFeedback=" + reponseFeedback +
                ", comment='" + comment + '\'' +
                '}';
    }
}
