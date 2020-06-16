package org.acme.hibernate.orm.domain;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "reponse")
@NamedQuery(name = "Reponses.findAll",
        query = "SELECT r FROM Reponse r ORDER BY r.text_reponse",
        hints = @QueryHint(name = "org.hibernate.cacheable", value = "true") )
@Cacheable
public class Reponse implements Serializable {

    @Id
    @SequenceGenerator(
            name = "reponseSequence",
            sequenceName = "rep_id_seq",
            allocationSize = 1,
            initialValue = 4)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "reponseSequence")
    private Long id_reponse;

    @Column
    private String text_reponse;

//    @ManyToOne
//    private Question question;

    @Column
    private boolean rep;

    @Column
    private Long count;

    public Reponse(String text_reponse, Question question, boolean rep, Long count) {
        this.text_reponse = text_reponse;
        this.rep = rep;
        this.count=count;
    }

    public Reponse() {
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

    public boolean isRep() {
        return rep;
    }

    public void setRep(boolean rep) {
        this.rep = rep;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "Reponse{" +
                "id_reponse=" + id_reponse +
                ", text_reponse='" + text_reponse + '\'' +
                ", rep=" + rep +
                ", count=" + count +
                '}';
    }
}