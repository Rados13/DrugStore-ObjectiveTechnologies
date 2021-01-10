package pl.edu.agh.to.drugstore.controller.clientOrder.MyOrders;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import lombok.Setter;
import pl.edu.agh.to.drugstore.command.clientorder.AddClientOrderCommand;
import pl.edu.agh.to.drugstore.command.clientorder.EditClientOrderCommand;
import pl.edu.agh.to.drugstore.command.clientorder.RemoveClientOrderCommand;
import pl.edu.agh.to.drugstore.controller.OverviewController;
import pl.edu.agh.to.drugstore.model.business.ClientOrder;
import pl.edu.agh.to.drugstore.model.business.OrderStatus;
import pl.edu.agh.to.drugstore.model.dao.ClientOrderDAO;
import pl.edu.agh.to.drugstore.model.dao.MedicationDAO;
import pl.edu.agh.to.drugstore.model.people.Person;
import pl.edu.agh.to.drugstore.presenter.Alerts;

import java.math.BigDecimal;
import java.util.*;

/**
 * Klasa interfejsu graficznego odpowiedzialna za wyświetlanie wszystkich zamówień klientów dostępnych w bazie danych.
 */
@Setter
public class MyOrdersOverviewController extends OverviewController<ClientOrder> {

    private ClientOrderDAO clientOrderDAO;

    private MedicationDAO medicationDAO;

    @Setter
    private MyOrdersAppController appController;

    @FXML
    private TableColumn<ClientOrder, Date> submissionDateColumn;

    @FXML
    private TableColumn<ClientOrder, Date> shippingDateColumn;

    @FXML
    private TableColumn<ClientOrder, Integer> amountOfMedicinesOrderedColumn;

    @FXML
    private TableColumn<ClientOrder, String> clientLastNameColumn;

    @FXML
    private TableColumn<ClientOrder, BigDecimal> summedPriceColumn;

    @FXML
    private TableColumn<ClientOrder, OrderStatus> orderStatusTableColumn;

    /**
     * Inicjalizuje główne okno aplikacji, w którym wyświetlane są zamówienia klienta zapisane w bazie danych.
     */
    @Override
    protected void initialize() {
        startInitialize();
        tableView.getSelectionModel().setSelectionMode(
                SelectionMode.MULTIPLE);

        submissionDateColumn.setCellValueFactory(dataValue -> dataValue.getValue().getSubmissionDateProperty());
        shippingDateColumn.setCellValueFactory(dataValue -> dataValue.getValue().getShippingDateProperty());
        clientLastNameColumn.setCellValueFactory(dataValue -> {
            Person client = dataValue.getValue().getClientProperty().getValue();
            return client != null ? client.getLastNameProperty() : null;
        });
        amountOfMedicinesOrderedColumn.setCellValueFactory(dataValue -> dataValue.getValue().getMedicationsNumProperty());
        summedPriceColumn.setCellValueFactory(dataValue -> dataValue.getValue().getSumPriceProperty());
        orderStatusTableColumn.setCellValueFactory(dataValue -> dataValue.getValue().getOrderStatus());

    }

    /**
     * Odpowiada za obsługę eventu - naciśnięcie przycisku delete odpowiedzialnego za usuwanie zamówienia klienta z bazy danych.
     *
     * @param event
     */
    @Override
    protected void handleDeleteAction(ActionEvent event) {
        List<ClientOrder> clientOrdersToRemove = new ArrayList<>(List.copyOf(tableView.getSelectionModel().getSelectedItems()));
        ListIterator<ClientOrder> iterator = clientOrdersToRemove.listIterator();
        while (iterator.hasNext()) {
            ClientOrder order = iterator.next();
            if (!order.getOrderStatus().getValue().equals(OrderStatus.PLACED)) {
                Alerts.showErrorAlert("We are sorry", "You tried to cancel order with status " + order.getOrderStatus().getValue(), "You can't cancel this order");
                iterator.remove();
            }
        }
//            for (ClientOrder order : clientOrdersToRemove) {
//                if (order.getOrderStatus().getValue() != OrderStatus.PLACED) {
//                    Alerts.showErrorAlert("We are sorry", "You tried to cancel order with status " + order.getOrderStatus().getValue(), "You can't cancel this order");
//                    clientOrdersToRemove.remove(order);
//                }
//            }

        if (clientOrdersToRemove.size() > 0) {
            RemoveClientOrderCommand removePeopleCommand = new RemoveClientOrderCommand(clientOrdersToRemove, clientOrderDAO);
            commandRegistry.executeCommand(removePeopleCommand);
        }
        refresh();
    }

    /**
     * Odpowiada za obsługę eventu - naciśnięcie przycisku edit odpowiedzialnego za edytowanie danych wybranego zamówienia klienta.
     * Wyświetla osobne okno w interfejsie graficznym, które umożliwia edycję danych.
     *
     * @param event
     * @throws InterruptedException
     */
    @Override
    protected void handleEditAction(ActionEvent event) throws InterruptedException {
        ClientOrder clientOrderToEdit = tableView.getSelectionModel()
                .getSelectedItem();
        ClientOrder editedClientOrder = clientOrderToEdit;
        if (clientOrderToEdit.getOrderStatus().getValue() != OrderStatus.PAID && clientOrderToEdit.getOrderStatus().getValue() != OrderStatus.PLACED) {
            Alerts.showErrorAlert("We are sorry", "You can't edit order if status is different than placed or paid", "Please contact us");
            return;
        }

        if (clientOrderToEdit != null) {
            appController.showClientOrderEditDialog(editedClientOrder);
            EditClientOrderCommand editPersonCommand = new EditClientOrderCommand(clientOrderToEdit, editedClientOrder, clientOrderDAO);
            commandRegistry.executeCommand(editPersonCommand);
        }
        refresh();
    }

    /**
     * Odpowiada za obsługę eventu - naciśnięcie przycisku add odpowiedzialnego za dodawanie zamówienia klienta do bazy danych.
     *
     * @param event
     */
    @Override
    protected void handleAddAction(ActionEvent event) {
        ClientOrder clientOrder = new ClientOrder();
        if (appController.showClientOrderEditDialog(clientOrder)) {
            AddClientOrderCommand addClientOrderCommand = new AddClientOrderCommand(clientOrder, clientOrderDAO);
            commandRegistry.executeCommand(addClientOrderCommand);
        }
        if (clientOrder.getPerson() != null && clientOrder.getPerson().getId() != 0) {
            allExisting.add(clientOrder);
            refresh();
        }

    }

    @Override
    protected void handleExitAction(ActionEvent event) {
        appController.getPrimaryStage().close();
        appController.getAppController().showClientPanel();
    }

    @Override
    protected void setData() {

    }

    protected void setData(Person person) {
        allExisting = FXCollections.observableArrayList(appController.getClientOrderDAO().findAllClientOrders(person.getId()));
        tableView.refresh();
        tableView.setItems(allExisting);
    }

}
