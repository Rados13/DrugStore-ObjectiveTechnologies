package pl.edu.agh.to.drugstore.filters;

import pl.edu.agh.to.drugstore.model.medications.Medication;

public class QuantityMedicationFilter implements MedicationFilter {

    private final Integer lowerBound;
    private final Integer upperBound;

    public QuantityMedicationFilter(Integer lowerBound, Integer upperBound) {
        this.lowerBound = lowerBound;
        this.upperBound = upperBound;
    }

    @Override
    public boolean matches(Medication medication) {
        return (lowerBound == null || medication.getQuantity() >= lowerBound)
                && (upperBound == null || medication.getQuantity() <= upperBound);
    }
}
