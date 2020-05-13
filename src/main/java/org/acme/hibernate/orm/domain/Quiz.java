package org.acme.hibernate.orm.domain;

import javax.persistence.*;

@Entity
@Table(name = "Quiz")
@NamedQuery(name = "Quiz.findAll",
        query = "SELECT q FROM Quiz q ORDER BY q.name",
        hints = @QueryHint(name = "org.hibernate.cacheable", value = "true") )
@Cacheable
public class Quiz {
    @Id
    @SequenceGenerator(
            name = "eventsSequence",
            sequenceName = "events_id_seq",
            allocationSize = 1,
            initialValue = 10)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "quizsSequence")
    private Integer id;

    @Column(length = 100, unique = true)
    private String name;

    @ManyToOne
    private Event event;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /*public Event getEvent() {
        return event;
    }*/

    public void setEvent(Event event) {
        this.event = event;
    }

    @Override
    public String toString() {
        return "Quiz{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", event=" + event +
                '}';
    }
}
