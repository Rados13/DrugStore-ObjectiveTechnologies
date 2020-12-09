package pl.edu.agh.to.drugstore.model.business;

import pl.edu.agh.to.drugstore.model.medications.Medication;
import pl.edu.agh.to.drugstore.model.people.Person;

import javax.persistence.*;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected int id;

    protected Date submissionDate;
    protected Date shippingDate;

    @ElementCollection
//    @MapKeyJoinColumn
    protected final HashMap<Medication, Tuple> medications = new LinkedHashMap<>();

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public HashMap<Medication, Tuple> getMedications() {
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
}
