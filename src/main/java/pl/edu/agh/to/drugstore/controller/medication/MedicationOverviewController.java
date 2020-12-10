package pl.edu.agh.to.drugstore.controller.medication;

import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import pl.edu.agh.to.drugstore.command.medicationCommands.AddMedicationCommand;
import pl.edu.agh.to.drugstore.command.Command;
import pl.edu.agh.to.drugstore.command.CommandRegistry;
import pl.edu.agh.to.drugstore.command.medicationCommands.EditMedicationCommand;
import pl.edu.agh.to.drugstore.command.medicationCommands.RemoveMedicationCommand;
import pl.edu.agh.to.drugstore.model.dao.MedicationDAO;
import pl.edu.agh.to.drugstore.model.medications.Medication;
import pl.edu.agh.to.drugstore.model.medications.MedicationForm;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Klasa interfejsu graficznego odpowiedzialna za wyświetlanie wszystkich lekarstw dostępnych w bazie danych.
 */
public class MedicationOverviewController {

    private MedicationAppController appController;

    private CommandRegistry commandRegistry;

    private MedicationDAO medicationDAO;

    ObservableList<Medication> allExisting;

    @FXML
    private TableView<Medication> medicationTableView;

    @FXML
    private TableColumn<Medication, String> nameColumn;

    @FXML
    private TableColumn<Medication, MedicationForm> formColumn;

    @FXML
    public TableColumn<Medication, Boolean> prescriptionRequiredColumn;

    @FXML
    public TableColumn<Medication, BigDecimal> priceColumn;

    @FXML
    public TableColumn<Medication, Integer> quantityColumn;

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

    /**
     * Inicjalizuje główne okno aplikacji, w którym wyświetlane są osoby zapisane w bazie danych.
     */
    @FXML
    private void initialize() {
        medicationTableView.getSelectionModel().setSelectionMode(
                SelectionMode.MULTIPLE);
        nameColumn.setCellValueFactory(dataValue -> dataValue.getValue()
                .getNameProperty());
        formColumn.setCellValueFactory(dataValue -> dataValue.getValue()
                .getFormProperty());
        prescriptionRequiredColumn.setCellValueFactory(dataValue -> dataValue.getValue()
                .getPrescriptionRequiredProperty());
        priceColumn.setCellValueFactory(dataValue -> dataValue.getValue()
                .getPriceProperty());
        quantityColumn.setCellValueFactory(dataValue -> dataValue.getValue()
                .getQuantityProperty());

        deleteButton.disableProperty().bind(
                Bindings.isEmpty(medicationTableView.getSelectionModel()
                        .getSelectedItems()));
        editButton.disableProperty().bind(
                Bindings.size(
                        medicationTableView.getSelectionModel()
                                .getSelectedItems()).isNotEqualTo(1));
    }

    /**
     * Odpowiada za obsługę eventu - naciśnięcie przycisku delete odpowiedzialnego za usuwanie osoby z bazy danych.
     *
     * @param event
     */
    @FXML
    private void handleDeleteAction(ActionEvent event) {
        List<Medication> medicationsToRemove = List.copyOf(medicationTableView.getSelectionModel().getSelectedItems());
        RemoveMedicationCommand removeMedicationCommand = new RemoveMedicationCommand(medicationsToRemove, medicationDAO);
        commandRegistry.executeCommand(removeMedicationCommand);
        for(Medication medication : medicationsToRemove){
            allExisting.remove(medication);
        }
        refresh();
    }

    /**
     * Odpowiada za obsługę eventu - naciśnięcie przycisku edit odpowiedzialnego za edytowanie danych wybranej osoby.
     * Wyświetla osobne okno w interfejsie graficznym, które umożliwia edycję danych.
     *
     * @param event
     * @throws InterruptedException
     */
    @FXML
    private void handleEditAction(ActionEvent event) throws InterruptedException {
        Medication medicationToEdit = medicationTableView.getSelectionModel()
                .getSelectedItem();
        Medication editedMedication = medicationToEdit;
        if (medicationToEdit != null) {
            appController.showMedicationEditDialog(editedMedication);
             EditMedicationCommand editPersonCommand = new EditMedicationCommand(editedMedication,medicationDAO);
            commandRegistry.executeCommand(editPersonCommand);
        }
        refresh();
    }

    /**
     * Odpowiada za obsługę eventu - naciśnięcie przycisku add odpowiedzialnego za dodawanie osoby do bazy danych.
     *
     * @param event
     */
    @FXML
    private void handleAddAction(ActionEvent event) {
        Medication medication = new Medication();
        if (appController.showMedicationEditDialog(medication)) {
            AddMedicationCommand addMedicationCommand = new AddMedicationCommand(medication, medicationDAO);
            commandRegistry.executeCommand(addMedicationCommand);
        }
        if(!allExisting.stream().map(Medication::getName).collect(Collectors.toList()).contains(medication.getName()))
            allExisting.add(medication);
        refresh();
    }

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

    public void setData() {
        allExisting = FXCollections.observableArrayList(appController.getMedicationDAO().findAll());
        System.out.println(allExisting);
        medicationTableView.refresh();
        medicationTableView.setItems(allExisting);
    }

    void refresh() {
        medicationTableView.refresh();
    }

    public void setAppController(MedicationAppController appController) {
        this.appController = appController;
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

    public void setMedicationDAO(MedicationDAO medicationDAO) {
        this.medicationDAO = medicationDAO;
    }
}
