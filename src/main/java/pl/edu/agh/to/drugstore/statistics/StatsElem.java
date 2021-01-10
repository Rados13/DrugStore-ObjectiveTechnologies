package pl.edu.agh.to.drugstore.statistics;

import javafx.beans.property.StringProperty;

public interface StatsElem {

    StringProperty getStatName();

    StringProperty getStatValue();

    StringProperty getAdditionalValue();
}
