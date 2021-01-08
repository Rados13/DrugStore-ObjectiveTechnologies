package pl.edu.agh.to.drugstore.filters;

import pl.edu.agh.to.drugstore.model.medications.Medication;

public class NameMedicationFilter implements MedicationFilter {

    private final String phrase;

    public NameMedicationFilter(String phrase) {
        this.phrase = phrase.toLowerCase();
    }

    @Override
    public boolean matches(Medication medication) {
        if (phrase == null || phrase.equals("")) return true;
        return medication.getName().toLowerCase().contains(phrase);
    }
}
