package pl.edu.agh.to.drugstore.model.medications;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.LinkedHashSet;

@Entity
@Builder
@Getter
@AllArgsConstructor
public class Medication {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String name;

    private MedicationForm form;

    private boolean prescriptionRequired;

    private BigDecimal price;

    private Integer quantity;

    @ManyToMany()
    private final Collection<Illness> illnessesToCure = new LinkedHashSet<>();

    @ElementCollection
    private final Collection<String> ingredients = new LinkedHashSet<>();

    public Medication() {
        this.name = "";
        this.form = null;
        this.prescriptionRequired = false;
        this.price = BigDecimal.valueOf(0);
        this.quantity = 0;
    }


    public Medication(String name, MedicationForm medicationForm, boolean b, BigDecimal v, int quantity) {
        this.name = name;
        this.form = medicationForm;
        this.prescriptionRequired = b;
        this.price = v;
        this.quantity = quantity;
    }


    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public static String formatMedicationName(String s){
        return s.substring(0,1).toUpperCase() + s.substring(1).toLowerCase();
    }

    public ObservableValue<String> getNameProperty() {
        return new SimpleStringProperty(name);
    }
    public ObservableValue<MedicationForm> getFormProperty() {
        return new SimpleObjectProperty<MedicationForm>(form);
    }
    public ObservableValue<Boolean> getPrescriptionRequiredProperty() {
        return new SimpleObjectProperty<Boolean>(prescriptionRequired);
    }

    public ObservableValue<BigDecimal> getPriceProperty() {
        return new SimpleObjectProperty<BigDecimal>(price);
    }

    public ObservableValue<Integer> getQuantityProperty() {
        return new SimpleObjectProperty<Integer>(quantity);
    }
}
