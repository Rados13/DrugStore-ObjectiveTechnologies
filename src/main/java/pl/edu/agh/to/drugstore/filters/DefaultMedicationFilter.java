package pl.edu.agh.to.drugstore.filters;


import pl.edu.agh.to.drugstore.model.medications.Medication;

public class DefaultMedicationFilter implements MedicationFilter {

    @Override
    public boolean matches(Medication medication) {
        return true;
    }
}
