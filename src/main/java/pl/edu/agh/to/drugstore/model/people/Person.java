package pl.edu.agh.to.drugstore.model.people;

import lombok.*;
import pl.edu.agh.to.drugstore.model.business.Notification;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@ToString
public class Person {

    public Person() {}

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private int id;

    private String firstname;
    private String lastname;
    private Date birthdate;

    @Column(unique = true)
    private String PESEL;

    @OneToOne()
    private Address address;
    @NonNull()
    private Role role;

    @OneToMany()
    private List<Notification> notificationList = new ArrayList<>();

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getPESEL() {
        return PESEL;
    }

    public void setPESEL(String PESEL) {
        this.PESEL = PESEL;
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

    public Collection<Notification> getNotificationList() {
        return notificationList;
    }
}
