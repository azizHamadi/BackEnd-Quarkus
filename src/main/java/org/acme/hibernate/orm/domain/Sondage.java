package org.acme.hibernate.orm.domain;

import javax.persistence.*;

@Entity
@Table(name = "sondage")
@NamedQuery(name = "Sondage.findAll",
        query = "SELECT s FROM Sondage s ORDER BY s.name",
        hints = @QueryHint(name = "org.hibernate.cacheable", value = "true") )
@Cacheable
public class Sondage {

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

    @ManyToOne
    private UserDTO user;

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

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Sondage{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", event=" + event +
                ", user=" + user +
                '}';
    }
}
