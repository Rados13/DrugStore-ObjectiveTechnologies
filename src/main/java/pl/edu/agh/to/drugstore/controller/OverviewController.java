package pl.edu.agh.to.drugstore.controller;

import javafx.beans.binding.Bindings;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TableView;
import lombok.Setter;
import pl.edu.agh.to.drugstore.command.Command;
import pl.edu.agh.to.drugstore.command.CommandRegistry;

/**
 * Klasa abstrakcyjna interfejsu graficznego odpowiedzialna za wyświetlanie wszystkich elementów któregoś modelu
 * dostępnych w bazie danych.
 */
abstract public class OverviewController<A> {

    public CommandRegistry commandRegistry;

    protected ObservableList<A> allExisting;

    @FXML
    protected TableView<A> tableView;

    public void startInitialize(){
        editButton.setOnAction(event -> {
            try {
                handleEditAction(event);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        deleteButton.setOnAction(this::handleDeleteAction);
        addButton.setOnAction(this::handleAddAction);

        deleteButton.disableProperty().bind(
                Bindings.isEmpty(tableView.getSelectionModel()
                        .getSelectedItems()));
        editButton.disableProperty().bind(
                Bindings.size(
                        tableView.getSelectionModel()
                                .getSelectedItems()).isNotEqualTo(1));
    }


    @FXML
    protected ListView<Command> commandLogView;

    @FXML
    protected Button deleteButton;

    @FXML
    protected Button editButton;

    @FXML
    protected Button addButton;

    @FXML
    protected Button undoButton;

    @FXML
    protected Button redoButton;

    /**
     * Inicjalizuje główne okno aplikacji, w którym wyświetlane są przedstawiciele danego maodelu zapisani w bazie danych.
     */
    @FXML
    protected abstract void initialize();

    /**
     * Odpowiada za obsługę eventu - naciśnięcie przycisku delete odpowiedzialnego za
     * usuwanie przedstawiciela modelu z bazy danych.
     *
     * @param event
     */
    @FXML
    protected abstract void handleDeleteAction(ActionEvent event);

    /**
     * Odpowiada za obsługę eventu - naciśnięcie przycisku edit odpowiedzialnego za edytowanie danych
     * wybranego przedstawiciela modelu. Wyświetla osobne okno w interfejsie graficznym, które umożliwia edycję danych.
     *
     * @param event
     * @throws InterruptedException
     */
    @FXML
    protected abstract void handleEditAction(ActionEvent event) throws InterruptedException;

    /**
     * Odpowiada za obsługę eventu - naciśnięcie przycisku add odpowiedzialnego za dodawanie
     * nowego przedstawiciela modelu do bazy danych.
     *
     * @param event
     */
    @FXML
    protected abstract void handleAddAction(ActionEvent event);

    @FXML
    protected void handleUndoAction(ActionEvent event) {
        commandRegistry.undo();
        refresh();
    }

    @FXML
    protected void handleRedoAction(ActionEvent event) {
        commandRegistry.redo();
        refresh();
    }

    protected abstract void setData();

    protected void refresh() {
        tableView.refresh();
    }

    public void setCommandRegistry(CommandRegistry commandRegistry) {
        this.commandRegistry = commandRegistry;
        commandLogView.setItems(commandRegistry.getCommandStack());
        commandLogView.setCellFactory(lv -> new ListCell<Command>() {
            protected void updateItem(Command item, boolean empty) {
                super.updateItem(item, empty);
                setText((item != null && !empty) ? item.getName() : null);
            }
        });
    }



}
