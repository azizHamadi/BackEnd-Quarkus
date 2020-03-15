package org.acme.hibernate.orm;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "Event")
@NamedQuery(name = "Events.findAll",
        query = "SELECT e FROM Event e ORDER BY e.name",
        hints = @QueryHint(name = "org.hibernate.cacheable", value = "true") )
@Cacheable
public class Event {
    @Id
    @SequenceGenerator(
            name = "eventsSequence",
            sequenceName = "events_id_seq",
            allocationSize = 1,
            initialValue = 10)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "eventsSequence")
    private Integer id;

    @Column(length = 100, unique = true)
    private String name;

    @Column(unique = false)
    private String description;

    @Column(unique = false)
    private Date startDate;

    @Column(unique = false)
    private Date endDate;

    @Column(unique = false)
    private String type;

    @Column(length = 100000,unique = false)
    private String image;

    @Column(unique = false)
    private String status;

    public Event() {
    }

    public Event(String name, Date startDate, Date endDate, String type, String image, String status, String description) {
        this.name = name ;
        this.startDate = startDate ;
        this.endDate = endDate ;
        this.type = type ;
        this.image = image ;
        this.status = status ;
        this.description = description ;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

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

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
