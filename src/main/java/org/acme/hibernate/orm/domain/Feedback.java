package org.acme.hibernate.orm.domain;
import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "Feedback")
@NamedQuery(name = "Feedback.findAll",
        query = "SELECT f FROM Feedback f ORDER BY f.name",
        hints = @QueryHint(name = "org.hibernate.cacheable", value = "true") )
@Cacheable
public class Feedback implements Serializable {
    @Id
    @SequenceGenerator(
            name = "feedbacksSequence",
            sequenceName = "feedbacks_id_seq",
            allocationSize = 1,
            initialValue = 10)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "feedbacksSequence")
    private int id;

    @Column(length = 100, unique = true)
    private String name;

    @ManyToOne
    private Event event;

    @Column
    private boolean status;

    public Feedback() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Feedback{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", event=" + event +
                ", status=" + status +
                '}';
    }
}