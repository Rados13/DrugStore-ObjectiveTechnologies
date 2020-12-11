package pl.edu.agh.to.drugstore.model.business;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.util.Pair;
import lombok.Setter;
import pl.edu.agh.to.drugstore.model.medications.Medication;
import pl.edu.agh.to.drugstore.model.people.Person;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Setter
public abstract class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected int id;

    protected Date submissionDate;

    protected Date shippingDate;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @MapKey(name = "id")
    protected Map<Medication, Tuple> medications = new LinkedHashMap<>();

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public Map<Medication, Tuple> getMedications() {
        return medications;
    }

    public Date getSubmissionDate() {
        return submissionDate;
    }

    public void setSubmissionDate(Date submissionDate) {
        this.submissionDate = submissionDate;
    }

    public Date getShippingDate() {
        return shippingDate;
    }

    public void setShippingDate(Date shippingDate) {
        this.shippingDate = shippingDate;
    }

    public boolean shipped() {
        return this.shippingDate != null;
    }

    public void bookMedication(Medication medication) {
        this.medications.get(medication).setBooked(true);
    }


    public ObservableValue<Date> getShippingDateProperty() {return new SimpleObjectProperty<Date>(shippingDate);}
    public ObservableValue<Date> getSubmissionDateProperty() {return new SimpleObjectProperty<Date>(submissionDate);}
    public ObservableValue<Integer> getMedicationsNumProperty() {return new SimpleObjectProperty<Integer>(medications.size());}

    public List<Pair<Medication,Tuple>> getMedicationsAsList() {
        return medications.entrySet().stream()
                .map(elem -> new Pair<Medication,Tuple>(elem.getKey(),elem.getValue())).collect(Collectors.toList());
    }
}
