package org.acme.hibernate.orm.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "Aime")
@NamedQuery(name = "aime.findAll",
        query = "SELECT l FROM Aime l",
        hints = @QueryHint(name = "org.hibernate.cacheable", value = "true") )
@Cacheable
public class Aime implements Serializable {

    @Id
    @ManyToOne
    UserDTO userDTO;

    @Id
    @ManyToOne
    QuestionMessage messageQuestion;

    public UserDTO getUserDTO() {
        return userDTO;
    }

    public void setUserDTO(UserDTO userDTO) {
        this.userDTO = userDTO;
    }

    public QuestionMessage getMessageQuestion() {
        return messageQuestion;
    }

    public void setMessageQuestion(QuestionMessage messageQuestion) {
        this.messageQuestion = messageQuestion;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Aime like = (Aime) o;
        return userDTO.equals(like.userDTO) &&
                messageQuestion.equals(like.messageQuestion);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userDTO, messageQuestion);
    }
}
