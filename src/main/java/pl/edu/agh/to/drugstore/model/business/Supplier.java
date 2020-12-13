package pl.edu.agh.to.drugstore.model.business;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.ToString;
import pl.edu.agh.to.drugstore.model.medications.Medication;

import javax.persistence.*;
import java.util.Collection;
import java.util.LinkedHashSet;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Supplier {

    @ManyToMany
    private final Collection<Medication> suppliedMedications = new LinkedHashSet<>();
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(nullable = false)
    private String NIP;
    private String name;

    public Supplier(Supplier supplier) {
        this.id = supplier.getId();
        this.name = supplier.getName();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Collection<Medication> getSuppliedMedications() {
        return suppliedMedications;
    }

    public String getNIP() {
        return NIP;
    }

    public void setNIP(String NIP) {
        this.NIP = NIP;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ObservableValue<String> getNameProperty() {
        return new SimpleStringProperty(name);
    }

    public ObservableValue<String> getNIPProperty() {
        return new SimpleStringProperty(NIP);
    }

    public ObservableValue<Number> getIdProperty() {
        return new SimpleIntegerProperty(id);
    }
}
