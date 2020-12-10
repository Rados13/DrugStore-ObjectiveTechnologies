package pl.edu.agh.to.drugstore.controller.supplier;

import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import lombok.Data;
import pl.edu.agh.to.drugstore.command.Command;
import pl.edu.agh.to.drugstore.command.CommandRegistry;
import pl.edu.agh.to.drugstore.command.supplier.AddSupplierCommand;
import pl.edu.agh.to.drugstore.command.supplier.EditSupplierCommand;
import pl.edu.agh.to.drugstore.command.supplier.RemoveSupplierCommand;
import pl.edu.agh.to.drugstore.model.business.Supplier;
import pl.edu.agh.to.drugstore.model.dao.SupplierDAO;

import java.util.List;

@Data
public class SupplierOverviewController {

    private SupplierAppController appController;

    private CommandRegistry commandRegistry;

    private SupplierDAO supplierDAO;

    ObservableList<Supplier> allExisting;

    @FXML
    private TableView<Supplier> supplierTableView;

    @FXML
    private TableColumn<Supplier, Number> IdColumn;

    @FXML
    private TableColumn<Supplier, String> NameColumn;

    @FXML
    private TableColumn<Supplier, String> NIPColumn;

    @FXML
    private ListView<Command> commandLogView;

    @FXML
    private Button deleteButton;

    @FXML
    private Button editButton;

    @FXML
    private Button addButton;

    @FXML
    private Button undoButton;

    @FXML
    private Button redoButton;

    @FXML
    private void initialize() {
        supplierTableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        IdColumn.setCellValueFactory(dataValue -> dataValue.getValue().getIdProperty());
        NameColumn.setCellValueFactory(dataValue -> dataValue.getValue().getNameProperty());
        NIPColumn.setCellValueFactory(dataValue -> dataValue.getValue().getNIPProperty());

        deleteButton.disableProperty().bind(
                Bindings.isEmpty(supplierTableView.getSelectionModel().getSelectedItems()));
        editButton.disableProperty().bind(
                Bindings.size(supplierTableView.getSelectionModel().getSelectedItems())
                        .isNotEqualTo(1));
    }

    @FXML
    public void handleDeleteAction(ActionEvent actionEvent) {
        List<Supplier> suppliersToRemove = List.copyOf(supplierTableView.getSelectionModel().getSelectedItems());
        RemoveSupplierCommand removeSupplierCommand = new RemoveSupplierCommand(suppliersToRemove, supplierDAO);
        commandRegistry.executeCommand(removeSupplierCommand);
        suppliersToRemove.forEach(supplier -> allExisting.remove(supplier));
        supplierTableView.refresh();
    }

    @FXML
    public void handleEditAction(ActionEvent actionEvent) {
        Supplier supplierToEdit = supplierTableView.getSelectionModel().getSelectedItem();
        Supplier editedSupplier = supplierToEdit;
        if (supplierToEdit != null) {
            appController.showSupplierEditDialog(editedSupplier);
            EditSupplierCommand editSupplierCommand = new EditSupplierCommand(supplierToEdit, editedSupplier, supplierDAO);
            commandRegistry.executeCommand(editSupplierCommand);
        }
        supplierTableView.refresh();
    }

    @FXML
    public void handleAddAction(ActionEvent actionEvent) {
        Supplier supplier = new Supplier();
        if (appController.showSupplierEditDialog(supplier)) {
            AddSupplierCommand addSupplierCommand = new AddSupplierCommand(supplier, supplierDAO);
            commandRegistry.executeCommand(addSupplierCommand);
        }
        allExisting.add(supplier);
        supplierTableView.refresh();
    }

    public void setData() {
        allExisting = FXCollections.observableArrayList(appController.getSupplierDAO().findAll());
        System.out.println(allExisting);
        supplierTableView.refresh();
        supplierTableView.setItems(allExisting);
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
