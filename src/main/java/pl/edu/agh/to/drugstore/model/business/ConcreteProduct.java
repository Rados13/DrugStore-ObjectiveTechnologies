package pl.edu.agh.to.drugstore.model.business;

import pl.edu.agh.to.drugstore.model.medications.Medication;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class ConcreteProduct {

    public ConcreteProduct() {}

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @ManyToOne()
    private Medication medication;

    private LocalDate expirationDate;

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDate expirationDate) {
        if (expirationDate.isBefore(LocalDate.now()))
            throw new IllegalStateException("Medication expiry date is exceeded");
        this.expirationDate = expirationDate;
    }

    public Medication getMedication() {
        return medication;
    }

    public void setMedication(Medication medication) {
        this.medication = medication;
    }
}
