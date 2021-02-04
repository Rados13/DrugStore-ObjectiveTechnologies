package pl.edu.agh.to.drugstore.model.medications;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import pl.edu.agh.to.drugstore.model.business.Supplier;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.LinkedHashSet;

@Entity
@Builder
@Getter
@AllArgsConstructor
public class Medication {

    @ManyToMany
    private final Collection<Illness> illnessesToCure = new LinkedHashSet<>();
    @ManyToMany
    private final Collection<Supplier> suppliers = new LinkedHashSet<>();
    @ElementCollection
    private final Collection<String> ingredients = new LinkedHashSet<>();
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String name;
    private MedicationForm form;
    private boolean prescriptionRequired;
    private BigDecimal price;
    private Integer quantity;

    public Medication() {
        this.name = "";
        this.form = null;
        this.prescriptionRequired = false;
        this.price = BigDecimal.valueOf(0);
        this.quantity = 0;
    }


    public Medication(String name, MedicationForm medicationForm, boolean b, BigDecimal v, int quantity) {
        this.name = formatMedicationName(name);
        this.form = medicationForm;
        this.prescriptionRequired = b;
        this.price = v;
        this.quantity = quantity;
    }

    public static String formatMedicationName(String s) {
        return s.substring(0, 1).toUpperCase() + s.substring(1).toLowerCase();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = formatMedicationName(name);
    }

    public MedicationForm getForm() {
        return form;
    }

    public void setForm(MedicationForm form) {
        this.form = form;
    }

    public Collection<String> getIngredients() {
        return ingredients;
    }

    public Collection<Illness> getIllnessesToCure() {
        return illnessesToCure;
    }

    public Collection<Supplier> getSuppliers() {
        return suppliers;
    }

    public void addIllness(Illness il) {
        il.addMedication(this);
        this.illnessesToCure.add(il);
    }

    public boolean isPrescriptionRequired() {
        return prescriptionRequired;
    }

    public void setPrescriptionRequired(boolean prescriptionRequired) {
        this.prescriptionRequired = prescriptionRequired;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        if (price.compareTo(new BigDecimal("0.0")) < 0)
            throw new IllegalArgumentException("Price cannot be negative");
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public ObservableValue<String> getNameProperty() {
        return new SimpleStringProperty(name);
    }

    public ObservableValue<MedicationForm> getFormProperty() {
        return new SimpleObjectProperty<>(form);
    }

    public ObservableValue<Boolean> getPrescriptionRequiredProperty() {
        return new SimpleObjectProperty<>(prescriptionRequired);
    }

    public ObservableValue<BigDecimal> getPriceProperty() {
        return new SimpleObjectProperty<>(price);
    }

    public ObservableValue<Integer> getQuantityProperty() {
        return new SimpleObjectProperty<>(quantity);
    }

    @Override
    public String toString() {
        return name + ", " + form + ", " + price + " zl";
    }
}
