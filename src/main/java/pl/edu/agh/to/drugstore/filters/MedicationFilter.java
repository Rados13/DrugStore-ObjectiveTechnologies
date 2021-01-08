package pl.edu.agh.to.drugstore.filters;

import pl.edu.agh.to.drugstore.model.medications.Medication;

public interface MedicationFilter {
    boolean matches(Medication medication);
}
