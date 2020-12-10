package pl.edu.agh.to.drugstore.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import lombok.Setter;
import pl.edu.agh.to.drugstore.command.CommandRegistry;
import pl.edu.agh.to.drugstore.command.clientorder.AddClientOrderCommand;
import pl.edu.agh.to.drugstore.command.clientorder.EditClientOrderCommand;
import pl.edu.agh.to.drugstore.command.clientorder.RemoveClientOrderCommand;
import pl.edu.agh.to.drugstore.model.business.ClientOrder;
import pl.edu.agh.to.drugstore.model.dao.ClientOrderDAO;

import java.util.Date;
import java.util.List;

@Setter
public class ClientOrderOverviewController extends OverviewController<ClientOrder>{

    private CommandRegistry commandRegistry;
    private ClientOrderDAO clientOrderDAO;

    ObservableList<ClientOrder> allClientOrders;

    private ClientOrderAppController appController;

    @FXML
    private TableColumn<ClientOrder, Date> submissionDateColumn;

    @FXML
    private TableColumn<ClientOrder, Date> shippingDateColumn;

    @FXML
    private TableColumn<ClientOrder, Integer> amountOfMedicinesOrderedColumn;

    @FXML
    private TableColumn<ClientOrder, String> clientLastNameColumn;

    @Override
    void initialize() {
        startInitialize();
        tableView.getSelectionModel().setSelectionMode(
                SelectionMode.MULTIPLE);

        submissionDateColumn.setCellValueFactory(dataValue -> dataValue.getValue().getSubmissionDateProperty());
        shippingDateColumn.setCellValueFactory(dataValue -> dataValue.getValue().getShippingDateProperty());
        clientLastNameColumn.setCellValueFactory(dataValue ->
                dataValue.getValue().getClientProperty().getValue().getLastNameProperty());
        amountOfMedicinesOrderedColumn.setCellValueFactory(dataValue -> dataValue.getValue().getMedicationsNumProperty());
    }

    @Override
    void handleDeleteAction(ActionEvent event) {
        List<ClientOrder> clientOrdersToRemove = List.copyOf(tableView.getSelectionModel().getSelectedItems());
        RemoveClientOrderCommand removePeopleCommand = new RemoveClientOrderCommand(clientOrdersToRemove, clientOrderDAO);
        commandRegistry.executeCommand(removePeopleCommand);
        for(ClientOrder person : clientOrdersToRemove){
            allExisting.remove(person);
        }
        refresh();
    }

    @Override
    void handleEditAction(ActionEvent event) throws InterruptedException {
        ClientOrder clientOrderToEdit = tableView.getSelectionModel()
                .getSelectedItem();
        ClientOrder editedClientOrder = clientOrderToEdit;
        if (clientOrderToEdit != null) {
            appController.showClientOrderEditDialog(editedClientOrder);
            EditClientOrderCommand editPersonCommand = new EditClientOrderCommand(clientOrderToEdit, editedClientOrder, clientOrderDAO);
            commandRegistry.executeCommand(editPersonCommand);
        }
        refresh();
    }

    @Override
    void handleAddAction(ActionEvent event) {
        ClientOrder clientOrder = new ClientOrder();
        if (appController.showClientOrderEditDialog(clientOrder)) {
            AddClientOrderCommand addClientOrderCommand = new AddClientOrderCommand(clientOrder, clientOrderDAO);
            commandRegistry.executeCommand(addClientOrderCommand);
        }
        allExisting.add(clientOrder);
        refresh();
    }

    @Override
    void setData() {
        allExisting = FXCollections.observableArrayList(appController.getClientOrderDAO().findAll());
        System.out.println(allExisting);
        tableView.refresh();
        tableView.setItems(allExisting);
    }

    public void setAppController(ClientOrderAppController controller){
        this.appController = controller;
    }
}
