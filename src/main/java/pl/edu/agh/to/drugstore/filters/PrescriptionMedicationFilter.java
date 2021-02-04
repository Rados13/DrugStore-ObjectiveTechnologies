package pl.edu.agh.to.drugstore.filters;

import pl.edu.agh.to.drugstore.model.medications.Medication;

public class PrescriptionMedicationFilter implements MedicationFilter {

    private final Boolean prescriptionRequired;

    public PrescriptionMedicationFilter(Boolean prescriptionRequired) {
        this.prescriptionRequired = prescriptionRequired;
    }

    @Override
    public boolean matches(Medication medication) {
        return prescriptionRequired == null || prescriptionRequired == medication.isPrescriptionRequired();
    }
}
