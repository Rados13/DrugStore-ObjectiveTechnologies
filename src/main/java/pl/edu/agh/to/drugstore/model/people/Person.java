package pl.edu.agh.to.drugstore.model.people;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import lombok.*;
import pl.edu.agh.to.drugstore.model.business.Notification;
import pl.edu.agh.to.drugstore.model.business.Order;

import javax.persistence.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.*;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@ToString
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String firstname;

    private String lastname;

    private LocalDate birthdate;

    @Column(unique = true)
    private String PESEL;

    @OneToOne
    private Address address;

    @NonNull
    private Role role;

    @OneToMany
    private Collection<Notification> notificationList = new ArrayList<>();

    @OneToMany
    private Collection<Order> orders = new LinkedHashSet<>();

    public Person() { }

    public Person(Person person) {
        this.firstname = person.firstname;
        this.lastname = person.lastname;
        this.birthdate = person.birthdate;
        this.PESEL = person.PESEL;
        this.role = person.role;
        this.address = person.address;
        this.notificationList = person.notificationList;
        this.orders = person.orders;
    }

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

    public LocalDate getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
    }

    public Collection<Notification> getNotificationList() {
        return notificationList;
    }

    public Collection<Order> getOrders() {
        return orders;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public static Person personBuilder(List<String> params) throws ParseException {
        if (params.size() < 6) {
            throw new IllegalArgumentException("Given number of arguments is too low.");
        }
        LocalDate date = !params.get(4).equals("-") ? LocalDate.from(new SimpleDateFormat("dd/MM/yyyy").parse(params.get(4)).toInstant()) : null;
        return Person
                .builder()
                .role(!params.get(1).equals("-") ?
                        Role.valueOf(params.get(1).toUpperCase()) : null)
                .firstname(!params.get(2).equals("-") ? params.get(2) : null)
                .lastname(!params.get(3).equals("-") ? params.get(3) : null)
                .birthdate(date)
                .PESEL(params.get(5))
                .address(Address.addressBuilder(params.subList(6, params.size())))
                .build();
    }

    public ObservableValue<String> getFirstNameProperty() {
        return new SimpleStringProperty(firstname);
    }

    public ObservableValue<String> getLastNameProperty() {
        return new SimpleStringProperty(lastname);
    }

    public ObservableValue<String> getPESELProperty() {
        return new SimpleStringProperty(PESEL);
    }

    public ObservableValue<LocalDate> getBirthdateProperty() {
        return new SimpleObjectProperty<LocalDate>(birthdate);
    }

    public ObservableValue<Role> getRoleProperty() {
        return new SimpleObjectProperty<Role>(role);
    }
}
