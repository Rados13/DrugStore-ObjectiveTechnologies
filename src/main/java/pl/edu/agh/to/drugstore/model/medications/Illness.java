package pl.edu.agh.to.drugstore.model.medications;

import javax.persistence.*;
import java.util.Collection;
import java.util.LinkedHashSet;

@Entity
public class Illness {

    @ManyToMany()
    private final Collection<Medication> medications = new LinkedHashSet<>();
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String name;

    public Illness() {
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
        this.name = name;
    }

    public Collection<Medication> getMedications() {
        return medications;
    }

    public void addMedication(Medication medication) {
        this.medications.add(medication);
    }
}
