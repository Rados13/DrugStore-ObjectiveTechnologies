package pl.edu.agh.to.drugstore.model.business;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import lombok.Getter;
import lombok.NonNull;
import pl.edu.agh.to.drugstore.model.medications.Medication;

import javax.persistence.*;

@Entity
@Getter
public class Tuple {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private int quantity;

    private boolean booked;

    @ManyToOne
    @JoinColumn(name = "medication_id")
    private Medication medication;

    public Tuple() {}

    public Tuple(int quantity, boolean booked,Medication medication) {
        this.quantity = quantity;
        this.booked = booked;
        this.medication = medication;
    }

    public int getQuantity() {
        return quantity;
    }



    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public boolean isBooked() {
        return booked;
    }

    public void setBooked(boolean booked) {
        this.booked = booked;
    }

    public ObservableValue<Integer> getQuantityProperty() {
        return new SimpleObjectProperty<Integer>(quantity);
    }
}
