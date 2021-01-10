package pl.edu.agh.to.drugstore.statistics;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import pl.edu.agh.to.drugstore.model.medications.Medication;

import java.util.Optional;

public class MedicationStats implements StatsElem {

    private final String statName;
    private final Medication medication;
    private final Optional<String> additionalStat;

    public MedicationStats(String name, Medication medication, Optional<String> additional){
        this.statName = name;
        this.medication = medication;
        this.additionalStat = additional;
    }

    @Override
    public StringProperty getStatName() { return new SimpleStringProperty(statName); }

    @Override
    public StringProperty getStatValue() { return new SimpleStringProperty(medication.toString()); }

    @Override
    public StringProperty getAdditionalValue() { return new SimpleStringProperty(additionalStat.orElseGet(() -> ""));}
}
