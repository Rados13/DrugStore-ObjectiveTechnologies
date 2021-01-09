package pl.edu.agh.to.drugstore.statistics;

import ch.qos.logback.core.net.server.Client;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.util.Pair;
import pl.edu.agh.to.drugstore.model.business.ClientOrder;
import pl.edu.agh.to.drugstore.model.business.Tuple;
import pl.edu.agh.to.drugstore.model.dao.ClientOrderDAO;
import pl.edu.agh.to.drugstore.model.medications.Medication;
import pl.edu.agh.to.drugstore.model.people.Person;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

public class orderStats {

    private final ClientOrderDAO clientOrderDAO;

    public orderStats(ClientOrderDAO clientOrderDAO) {
        this.clientOrderDAO = clientOrderDAO;
    }

    private ObservableList<ClientOrder> getListOfOrders() {
        return FXCollections.observableArrayList(clientOrderDAO.findAll());
    }

    public SimpleObjectProperty<BigDecimal> getAveragePriceOfOrder() {
        ObservableList<ClientOrder> orders = getListOfOrders();
        int amountOfOrders = orders.size();
        BigDecimal summed = orders
                .stream()
                .map(elem -> elem.getSumPriceProperty().getValue())
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        return new SimpleObjectProperty<BigDecimal>(summed.divide(new BigDecimal(amountOfOrders),2));
    }

    public SimpleObjectProperty<BigDecimal> getAverageAmountOfOrdersByClient(){
        ObservableList<ClientOrder> orders = getListOfOrders();
        BigDecimal clients = new BigDecimal(orders.stream()
                .map(ClientOrder::getPerson)
                .distinct()
                .count());
        return new SimpleObjectProperty<>(BigDecimal.valueOf(orders.size()).divide(clients,2));
    }

    public SimpleObjectProperty<Person> getClientWithMostOrders(){
        ObservableList<ClientOrder> orders = getListOfOrders();
        List<Person> persons = orders.stream()
                .map(ClientOrder::getPerson)
                .distinct()
                .collect(Collectors.toList());
        return new SimpleObjectProperty<>(persons.stream().map(person -> new Pair<Person,Long>(person,
                orders.stream().filter(elem -> elem.getPerson().equals(person)).count()))
                .reduce(new Pair<Person,Long>(null,0),
                        (first,second) -> first.getValue() > second.getValue()?first:second)
                .getKey());
    }

    private Integer getAmountOfMedicationFromTuples(Medication med,List<Tuple> tuples){
        return tuples.stream()
                .filter(elem -> elem.getMedication().equals(med))
                .mapToInt(Tuple::getQuantity)
                .sum();
    }

    public SimpleObjectProperty<Medication> getMostCommonBoughtMedicine(){
        ObservableList<ClientOrder> orders = getListOfOrders();
        List<Tuple> allTuples = orders.stream()
                .flatMap(elem -> elem.getMedications().stream())
                .collect(Collectors.toList());
        List<Medication> allMedications = allTuples.stream()
                .map(Tuple::getMedication)
                .distinct()
                .collect(Collectors.toList());

        return new SimpleObjectProperty<Medication>(allMedications.stream()
                .map(med -> new Pair<Medication,Integer>(med,getAmountOfMedicationFromTuples(med,allTuples)))
                .reduce(new Pair<Medication,Integer>(null,0),
                        (first,second) -> first.getValue() > second.getValue()?first:second)
                .getKey());
    }


}
