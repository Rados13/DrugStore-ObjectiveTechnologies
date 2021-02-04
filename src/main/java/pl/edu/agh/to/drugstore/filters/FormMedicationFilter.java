package pl.edu.agh.to.drugstore.filters;

import pl.edu.agh.to.drugstore.model.medications.Medication;
import pl.edu.agh.to.drugstore.model.medications.MedicationForm;

public class FormMedicationFilter implements MedicationFilter {

    private final MedicationForm form;

    public FormMedicationFilter(MedicationForm form) {
        this.form = form;
    }

    @Override
    public boolean matches(Medication medication) {
        return form == null || medication.getForm() == form;
    }
}
