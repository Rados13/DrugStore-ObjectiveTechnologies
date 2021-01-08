package pl.edu.agh.to.drugstore.filters;

import pl.edu.agh.to.drugstore.model.medications.Medication;

import java.util.Arrays;
import java.util.Objects;

public class CompositeMedicationFilter implements MedicationFilter {

    private final MedicationFilter[] filters;

    public CompositeMedicationFilter(MedicationFilter[] filters) {
        this.filters = filters;
    }

    @Override
    public boolean matches(Medication medication) {
        return Arrays.stream(filters).filter(Objects::nonNull).allMatch(filter -> filter.matches(medication));
    }
}
