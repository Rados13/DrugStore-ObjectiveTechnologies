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


    @FXML
    protected abstract void initialize();

    @FXML
    protected abstract void handleDeleteAction(ActionEvent event);

    @FXML
    protected abstract void handleEditAction(ActionEvent event) throws InterruptedException;

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
