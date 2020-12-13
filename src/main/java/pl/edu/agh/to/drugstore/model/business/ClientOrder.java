package pl.edu.agh.to.drugstore.model.business;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
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

    public ObservableValue<Person> getClientProperty() {
        return new SimpleObjectProperty<Person>(person);
    }
}
