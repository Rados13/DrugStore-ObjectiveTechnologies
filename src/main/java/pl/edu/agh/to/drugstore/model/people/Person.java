package pl.edu.agh.to.drugstore.model.people;

import lombok.*;
import pl.edu.agh.to.drugstore.model.business.Notification;

import javax.persistence.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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

    public static Person personBulder(List<String> params) throws ParseException {
        if (params.size() < 6) {
            System.out.println("Not proper number of params");
            return null;
        }
        Date date = !params.get(4).equals("-") ? new SimpleDateFormat("dd/MM/yyyy").parse(params.get(4)) : null;
        return Person
                .builder()
                .role(!params.get(1).equals("-")?
                        Role.valueOf(params.get(1).toUpperCase()):null)
                .firstname(!params.get(2).equals("-")?params.get(2):null)
                .lastname(!params.get(3).equals("-")?params.get(3):null)
                .birthdate(date)
                .PESEL(params.get(5))
                .address(Address.addressBuilder(params.subList(6, params.size())))
                .build();
    }
}
