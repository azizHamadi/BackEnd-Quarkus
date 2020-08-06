package org.acme.hibernate.orm.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "UserDTO")
@NamedQuery(name = "UserDTO.findAll",
        query = "SELECT u FROM UserDTO u ORDER BY u.id",
        hints = @QueryHint(name = "org.hibernate.cacheable", value = "true") )
@Cacheable
public class UserDTO implements Serializable {

    @Id
    private String id;

    @Column(length = 255, unique = true)
    private String username;

    @Column(length = 255, unique = true)
    private String email;

    @Column(length = 255)
    private String firstName;

    @Column(length = 255)
    private String lastName;

    @Column(length = 255)
    private String phoneNumber;

    @Column(length = 255)
    private boolean enabled = true;

    @OneToMany(mappedBy = "userDTO")
    List<Aime> likes;

    @OneToMany(mappedBy = "userDTO")
    List<ReponseSondageUser> reponseSondageUsers;

    @OneToMany(mappedBy = "userDTO")
    List<Word> words;

    @OneToMany(mappedBy = "userDTO")
    List<ReponseFeedBackUser> reponseFeedBackUsers;

    @OneToMany(mappedBy = "userDTO")
    List<Notification> notifications;

    public UserDTO() {
    }

    public UserDTO(String id, String email, String firstName, String lastName, String phoneNumber) {
        this.id = id;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
    }

    public UserDTO(String username, String email, String firstName, String lastName) {
        this.username = username;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public UserDTO(String username) {
        this.username = username;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public void setLikes(List<Aime> likes) {
        this.likes = likes;
    }

    public void setWords(List<Word> words) {
        this.words = words;
    }

    public void setReponseFeedBackUsers(List<ReponseFeedBackUser> reponseFeedBackUsers) {
        this.reponseFeedBackUsers = reponseFeedBackUsers;
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                "id='" + id + '\'' +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", enabled='" + enabled + '\'' +
                '}';
    }
}
