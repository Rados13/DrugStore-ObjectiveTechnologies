package pl.edu.agh.to.drugstore.model.business;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import lombok.Setter;
import pl.edu.agh.to.drugstore.model.medications.Medication;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @OrderBy("id")
    protected List<Tuple> medications = new ArrayList<>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Tuple> getMedications() {
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
        var result = this.medications
                .stream()
                .filter(elem -> elem.getMedication().equals(medication))
                .findFirst();
        result.ifPresent(tuple -> tuple.setBooked(true));
    }

    public ObservableValue<Date> getShippingDateProperty() {
        return new SimpleObjectProperty<Date>(shippingDate);
    }

    public ObservableValue<Date> getSubmissionDateProperty() {
        return new SimpleObjectProperty<Date>(submissionDate);
    }

    public ObservableValue<Integer> getMedicationsNumProperty() {
        return new SimpleObjectProperty<Integer>(medications.size());
    }

    public void updateMedications(List<Tuple> newList) {
        var toDelete = medications.stream().filter(elem -> !newList.contains(elem)).collect(Collectors.toList());
        medications.removeAll(toDelete);
        newList.forEach(elem -> {
            if (!medications.contains(elem)) medications.add(elem);
        });
    }

    public ObservableValue<BigDecimal> getSumPriceProperty() {
        return new SimpleObjectProperty<BigDecimal>(getMedications().stream()
                .map(elem -> elem.getMedication().getPrice().multiply(BigDecimal.valueOf(elem.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add));
    }


}
