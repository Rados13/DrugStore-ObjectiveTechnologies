package pl.edu.agh.to.drugstore.filters;

import pl.edu.agh.to.drugstore.model.medications.Medication;

import java.math.BigDecimal;

public class PriceMedicationFilter implements MedicationFilter {

    private final BigDecimal lowerBound;
    private final BigDecimal upperBound;

    public PriceMedicationFilter(BigDecimal lowerBound, BigDecimal upperBound) {
        this.lowerBound = lowerBound;
        this.upperBound = upperBound;
    }

    @Override
    public boolean matches(Medication medication) {
        return (lowerBound == null || medication.getPrice().compareTo(lowerBound) >= 0)
                && (upperBound == null || medication.getPrice().compareTo(upperBound) <= 0);
    }
}
