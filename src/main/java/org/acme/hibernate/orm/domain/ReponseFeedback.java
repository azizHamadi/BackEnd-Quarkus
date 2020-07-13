package org.acme.hibernate.orm.domain;

import javax.persistence.*;
import java.io.Serializable;
@Entity
@Table(name = "ReponseFeedback")
@NamedQuery(name = "ReponsesFeedback.findAll",
        query = "SELECT rf FROM ReponseFeedback rf ORDER BY rf.text_reponse",
        hints = @QueryHint(name = "org.hibernate.cacheable", value = "true") )
@Cacheable
public class ReponseFeedback implements Serializable {
    @Id
    @SequenceGenerator(
            name = "ReponseFeedback",
            sequenceName = "known_ReponsesFeedback_id_seq",
            initialValue = 4)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ReponseFeedback")
    private int id;

    @Column
    private String text_reponse;

    @Column
    private int ordre;

    public ReponseFeedback() {
    }

    public ReponseFeedback(String text_reponse, int ordre) {
        this.text_reponse = text_reponse;
        this.ordre = ordre;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText_reponse() {
        return text_reponse;
    }

    public void setText_reponse(String text_reponse) {
        this.text_reponse = text_reponse;
    }

    public int getOrdre() {
        return ordre;
    }

    public void setOrdre(int ordre) {
        this.ordre = ordre;
    }



    @Override
    public String toString() {
        return "ReponseFeedback{" +
                "id=" + id +
                ", text_reponse='" + text_reponse + '\'' +
                ", ordre=" + ordre +
                '}';
    }
}
