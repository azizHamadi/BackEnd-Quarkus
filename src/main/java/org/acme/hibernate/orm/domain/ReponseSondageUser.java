package org.acme.hibernate.orm.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "reponseSondageUser")
@NamedQuery(name = "reponseSondageUser.findAll",
        query = "SELECT r FROM ReponseSondageUser r",
        hints = @QueryHint(name = "org.hibernate.cacheable", value = "true") )
@Cacheable
public class ReponseSondageUser implements Serializable {

    @Id
    @ManyToOne
    UserDTO userDTO;

    @Id
    @ManyToOne
    ReponseQuestionSondage reponseQuestionSondage;

    public UserDTO getUserDTO() {
        return userDTO;
    }

    public void setUserDTO(UserDTO userDTO) {
        this.userDTO = userDTO;
    }

    public ReponseQuestionSondage getReponseQuestionSondage() {
        return reponseQuestionSondage;
    }

    public void setReponseQuestionSondage(ReponseQuestionSondage reponseQuestionSondage) {
        this.reponseQuestionSondage = reponseQuestionSondage;
    }

    @Override
    public String toString() {
        return "ReponseSondageUser{" +
                "userDTO=" + userDTO +
                ", reponseQuestionSondage=" + reponseQuestionSondage +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReponseSondageUser that = (ReponseSondageUser) o;
        return userDTO.equals(that.userDTO) &&
                reponseQuestionSondage.equals(that.reponseQuestionSondage);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userDTO, reponseQuestionSondage);
    }
}
