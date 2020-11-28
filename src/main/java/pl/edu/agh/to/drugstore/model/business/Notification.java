package pl.edu.agh.to.drugstore.model.business;

import pl.edu.agh.to.drugstore.model.people.Person;

import javax.persistence.*;

@Entity
public class Notification {

    public Notification() {}

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String notification;

    private int personId;

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getNotification() {
        return notification;
    }

    public void setNotification(String notification) {
        this.notification = notification;
    }

    public int getPersonId() {
        return personId;
    }

    public void setPersonId(int personId) {
        this.personId = personId;
    }
}
