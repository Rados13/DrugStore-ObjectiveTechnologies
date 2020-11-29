package pl.edu.agh.to.drugstore.model.medications;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collection;
import java.util.LinkedHashSet;

@Entity
@Builder
@Getter
@AllArgsConstructor
public class Medication {

    public Medication() {}

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String name;
    private MedicationForm form;
    private boolean prescriptionRequired;
    private BigDecimal price;

    @ManyToMany()
    private final Collection<Illness> illnessesToCure = new LinkedHashSet<>();

    @ElementCollection
    private final Collection<String> ingredients = new LinkedHashSet<>();

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

}
