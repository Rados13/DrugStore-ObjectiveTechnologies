package pl.edu.agh.to.drugstore.model.business;

import pl.edu.agh.to.drugstore.model.medications.Medication;

import javax.persistence.*;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected int id;

    protected Date submissionDate;
    protected Date shippingDate;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @MapKey(name = "id")
    protected final Map<Medication, Tuple> medications = new LinkedHashMap<>();

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
}
