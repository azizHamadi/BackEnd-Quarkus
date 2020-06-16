package org.acme.hibernate.orm.domain;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "wordCloud")
@NamedQuery(name = "WordCloud.findAll",
        query = "SELECT qw FROM WordCloud qw ORDER BY qw.text",
        hints = @QueryHint(name = "org.hibernate.cacheable", value = "true") )
@Cacheable
public class WordCloud {
    @Id
    @SequenceGenerator(
            name = "wordCloudsSequence",
            sequenceName = "wordClouds_id_seq",
            initialValue = 4)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "wordCloudsSequence")
    private Long id;

    @Column
    private String text;

    @Column(unique = false)
    private Boolean status;

    @ManyToOne
    private UserDTO user;

    @OneToMany(mappedBy = "wordCloud")
    List<Word> words;

    @ManyToOne
    private Event event;

    public WordCloud(String text, UserDTO user, Event event, Boolean status) {
        this.text = text ;
        this.user = user ;
        this.event = event ;
        this.status = status ;
    }

    public WordCloud() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public void setWords(List<Word> words) {
        this.words = words;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    @Override
    public String toString() {
        return "WordCloud{" +
                "id=" + id +
                ", text='" + text + '\'' +
                ", status=" + status +
                ", user=" + user +
                ", words=" + words +
                ", event=" + event +
                '}';
    }
}
