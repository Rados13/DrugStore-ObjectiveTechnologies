package pl.edu.agh.to.drugstore.statistics;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.math.BigDecimal;
import java.util.Optional;

public class NumericalStat implements StatsElem{

    private final String statName;
    private final BigDecimal statValue;
    private final Optional<String> additionalStat;

    public NumericalStat(String statName, BigDecimal statValue, Optional<String> additionalStat) {
        this.statName = statName;
        this.statValue = statValue;
        this.additionalStat = additionalStat;
    }


    @Override
    public StringProperty getStatName() {
        return new SimpleStringProperty(statName);
    }

    @Override
    public StringProperty getStatValue() {
        return new SimpleStringProperty(statValue.toString());
    }

    @Override
    public StringProperty getAdditionalValue() { return new SimpleStringProperty(additionalStat.orElseGet(() -> ""));}
}
