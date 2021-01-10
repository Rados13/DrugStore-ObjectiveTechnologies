package pl.edu.agh.to.drugstore.statistics;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import pl.edu.agh.to.drugstore.model.people.Person;

import java.util.Optional;

public class ClientStats implements StatsElem {

    private final String statName;
    private final Person person;
    private final Optional<String> additionalStat;

    public ClientStats(String statName, Person person, Optional<String> additionalStat) {
        this.statName = statName;
        this.person = person;
        this.additionalStat = additionalStat;
    }


    @Override
    public StringProperty getStatName() { return new SimpleStringProperty(statName); }

    @Override
    public StringProperty getStatValue() { return new SimpleStringProperty(person.getFirstname() + " "+ person.getLastname());  }

    @Override
    public StringProperty getAdditionalValue() { return new SimpleStringProperty(additionalStat.orElseGet(() -> "")); }
}
