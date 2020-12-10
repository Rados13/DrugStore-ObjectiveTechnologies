package pl.edu.agh.to.drugstore.model.business;

import lombok.NonNull;
import pl.edu.agh.to.drugstore.model.people.Person;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Entity
// set table name
public class ClientOrder extends Order {

    @OneToOne
    @NonNull
    private Person person;

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }
}
