package org.acme.hibernate.orm.domain;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "Notification")
@NamedQuery(name = "Notification.findAll",
        query = "SELECT n FROM Notification n",
        hints = @QueryHint(name = "org.hibernate.cacheable", value = "true") )
@Cacheable

public class Notification implements Serializable {

    @Id
    @ManyToOne
    UserDTO userDTO;

    @Id
    @ManyToOne
    Event event;

    @Column
    Boolean status;

    @Column(nullable = false)
    @CreationTimestamp
    private Date date;

    public Notification() {
    }

    public UserDTO getUserDTO() {
        return userDTO;
    }

    public void setUserDTO(UserDTO userDTO) {
        this.userDTO = userDTO;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Notification{" +
                "userDTO=" + userDTO +
                ", event=" + event +
                ", status=" + status +
                '}';
    }
}
