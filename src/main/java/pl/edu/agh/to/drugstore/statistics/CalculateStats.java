package pl.edu.agh.to.drugstore.statistics;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.util.Pair;
import pl.edu.agh.to.drugstore.model.business.ClientOrder;
import pl.edu.agh.to.drugstore.model.business.Tuple;
import pl.edu.agh.to.drugstore.model.dao.ClientOrderDAO;
import pl.edu.agh.to.drugstore.model.medications.Medication;
import pl.edu.agh.to.drugstore.model.people.Person;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class CalculateStats {

    private final ClientOrderDAO clientOrderDAO;

    public CalculateStats(ClientOrderDAO clientOrderDAO) {
        this.clientOrderDAO = clientOrderDAO;
    }

    private ObservableList<ClientOrder> getListOfOrders() {
        return FXCollections.observableArrayList(clientOrderDAO.findAll());
    }

    public List<StatsElem> getAllStats() {

        List<StatsElem> result = new ArrayList<>();
        ObservableList<ClientOrder> orders = getListOfOrders();
        result.add(getAveragePriceOfOrder(orders));
        result.add(getAverageAmountOfOrdersByClient(orders));
        result.add(getClientWithMostOrders(orders));
        result.add(getMostCommonBoughtMedicine(orders));
        result.add(getAverageAmountOfMedicinesPerOrder(orders));

        return result;
    }


    public StatsElem getAveragePriceOfOrder(ObservableList<ClientOrder> orders) {
        int amountOfOrders = orders.size();
        BigDecimal summed = orders
                .stream()
                .map(elem -> elem.getSumPriceProperty().getValue())
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        var result = summed.divide(new BigDecimal(amountOfOrders), 2, RoundingMode.CEILING);
        return new NumericalStat("Average price of order", result, Optional.empty());
    }

    public StatsElem getAverageAmountOfOrdersByClient(ObservableList<ClientOrder> orders) {
        BigDecimal clients = new BigDecimal(orders.stream()
                .map(ClientOrder::getPerson)
                .distinct()
                .count());
        var result = BigDecimal.valueOf(orders.size()).divide(clients, 2, RoundingMode.CEILING);
        return new NumericalStat("Average amount of order per client", result, Optional.empty());
    }

    public StatsElem getClientWithMostOrders(ObservableList<ClientOrder> orders) {
        List<Person> persons = orders.stream()
                .map(ClientOrder::getPerson)
                .distinct()
                .collect(Collectors.toList());
        var result = persons.stream().map(person -> new Pair<Person, Long>(person,
                orders.stream().filter(elem -> elem.getPerson().equals(person)).count()))
                .reduce(new Pair<Person, Long>(null, 0l),
                        (first, second) -> first.getValue() > second.getValue() ? first : second);

        return new ClientStats("Client with most orders", result.getKey(), Optional.of(result.getValue().toString()));
    }

    private Integer getAmountOfMedicationFromTuples(Medication med, List<Tuple> tuples) {
        return tuples.stream()
                .filter(elem -> elem.getMedication().equals(med))
                .mapToInt(Tuple::getQuantity)
                .sum();
    }

    public StatsElem getMostCommonBoughtMedicine(ObservableList<ClientOrder> orders) {
        List<Tuple> allTuples = orders.stream()
                .flatMap(elem -> elem.getMedications().stream())
                .collect(Collectors.toList());
        List<Medication> allMedications = allTuples.stream()
                .map(Tuple::getMedication)
                .distinct()
                .collect(Collectors.toList());

        var result = allMedications.stream()
                .map(med -> new Pair<Medication, Integer>(med, getAmountOfMedicationFromTuples(med, allTuples)))
                .reduce(new Pair<Medication, Integer>(null, 0), (first, second) -> first.getValue() > second.getValue() ? first : second);

        return new MedicationStats("Most common bought medicine", result.getKey(), Optional.of(result.getValue().toString()));

    }

    public StatsElem getAverageAmountOfMedicinesPerOrder(ObservableList<ClientOrder> orders) {
        long medicinesNum = orders.stream()
                .map(elem -> elem.getMedications().size())
                .reduce(0, Integer::sum)
                .longValue();
        var result = BigDecimal.valueOf(medicinesNum).divide(new BigDecimal(orders.size()), 2, RoundingMode.CEILING);
        return new NumericalStat("Average Amount of medicines per order", result, Optional.empty());

    }


}
