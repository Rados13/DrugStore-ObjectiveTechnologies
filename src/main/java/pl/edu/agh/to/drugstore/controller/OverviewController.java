package pl.edu.agh.to.drugstore.controller;

import javafx.beans.binding.Bindings;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TableView;
import pl.edu.agh.to.drugstore.command.Command;
import pl.edu.agh.to.drugstore.command.CommandRegistry;

abstract public class OverviewController<A> {

    CommandRegistry commandRegistry;

    ObservableList<A> allExisting;

    @FXML
    TableView<A> tableView;

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
    ListView<Command> commandLogView;

    @FXML
    Button deleteButton;

    @FXML
    Button editButton;

    @FXML
    Button addButton;

    @FXML
    private Button undoButton;

    @FXML
    private Button redoButton;


    @FXML
    abstract void initialize();

    @FXML
    abstract void handleDeleteAction(ActionEvent event);

    @FXML
    abstract void handleEditAction(ActionEvent event) throws InterruptedException;

    @FXML
    abstract void handleAddAction(ActionEvent event);

    @FXML
    private void handleUndoAction(ActionEvent event) {
        commandRegistry.undo();
        refresh();
    }

    @FXML
    private void handleRedoAction(ActionEvent event) {
        commandRegistry.redo();
        refresh();
    }

    abstract void setData();

    void refresh() {
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
