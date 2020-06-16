package org.acme.hibernate.orm.domain;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "reponseQuestionSondate")
@NamedQuery(name = "ReponseQuestionSondage.findAll",
        query = "SELECT rqs FROM ReponseQuestionSondage rqs ORDER BY rqs.text_reponse",
        hints = @QueryHint(name = "org.hibernate.cacheable", value = "true") )
@Cacheable
public class ReponseQuestionSondage {
    @Id
    @SequenceGenerator(
            name = "reponseQuestionSondageSequence",
            sequenceName = "repQuesSond_id_seq",
            allocationSize = 1,
            initialValue = 4)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "reponseQuestionSondageSequence")
    private Long id_reponse;

    @Column
    private String text_reponse;

    @OneToMany(mappedBy = "reponseQuestionSondage")
    List<ReponseSondageUser> reponseSondageUsers;

    public ReponseQuestionSondage(String text_reponse, Question question) {
        this.text_reponse = text_reponse;
    }

    public ReponseQuestionSondage() {
    }

    public Long getId_reponse() {
        return id_reponse;
    }

    public void setId_reponse(Long id_reponse) {
        this.id_reponse = id_reponse;
    }

    public String getText_reponse() {
        return text_reponse;
    }

    public void setText_reponse(String text_reponse) {
        this.text_reponse = text_reponse;
    }

    @Override
    public String toString() {
        return "Reponse{" +
                "id_reponse=" + id_reponse +
                ", text_reponse='" + text_reponse + '\'' +
                '}';
    }

}
