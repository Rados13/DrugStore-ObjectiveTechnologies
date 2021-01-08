package pl.edu.agh.to.drugstore.controller.medication;

import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import pl.edu.agh.to.drugstore.command.Command;
import pl.edu.agh.to.drugstore.command.CommandRegistry;
import pl.edu.agh.to.drugstore.command.medicationCommands.AddMedicationCommand;
import pl.edu.agh.to.drugstore.command.medicationCommands.EditMedicationCommand;
import pl.edu.agh.to.drugstore.command.medicationCommands.RemoveMedicationCommand;
import pl.edu.agh.to.drugstore.filters.*;
import pl.edu.agh.to.drugstore.model.dao.MedicationDAO;
import pl.edu.agh.to.drugstore.model.medications.Medication;
import pl.edu.agh.to.drugstore.model.medications.MedicationForm;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Klasa interfejsu graficznego odpowiedzialna za wyświetlanie wszystkich lekarstw dostępnych w bazie danych.
 */
public class MedicationOverviewController {

    @FXML
    public TableColumn<Medication, Boolean> prescriptionRequiredColumn;
    @FXML
    public TableColumn<Medication, BigDecimal> priceColumn;
    @FXML
    public TableColumn<Medication, Integer> quantityColumn;
    ObservableList<Medication> allExisting;
    private MedicationAppController medicationAppController;
    private CommandRegistry commandRegistry;
    private MedicationDAO medicationDAO;

    private Object[] filterOptions;
    private MedicationFilter filter;

    @FXML
    private TableView<Medication> medicationTableView;
    @FXML
    private TableColumn<Medication, String> nameColumn;
    @FXML
    private TableColumn<Medication, MedicationForm> formColumn;
    @FXML
    private ListView<Command> commandLogView;

    @FXML
    private Button deleteButton;

    @FXML
    private Button editButton;

    @FXML
    private Button addButton;

    @FXML
    private Button filterButton;

    @FXML
    private Button undoButton;

    @FXML
    private Button redoButton;

    @FXML
    private Button exitButton;

    /**
     * Inicjalizuje główne okno aplikacji, w którym wyświetlane są osoby zapisane w bazie danych.
     */
    @FXML
    private void initialize() {
        filterOptions = new Object[7];
        Arrays.fill(filterOptions, null);
        filter = new DefaultMedicationFilter();

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
        for (Medication medication : medicationsToRemove) {
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
            medicationAppController.showMedicationEditDialog(editedMedication);
            EditMedicationCommand editPersonCommand = new EditMedicationCommand(medicationToEdit,editedMedication, medicationDAO);
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
        if (medicationAppController.showMedicationEditDialog(medication)) {
            AddMedicationCommand addMedicationCommand = new AddMedicationCommand(medication, medicationDAO);
            commandRegistry.executeCommand(addMedicationCommand);
        }
        if (!allExisting.stream().map(Medication::getName).collect(Collectors.toList()).contains(medication.getName()) &&
                medication.getName() != null && !medication.getName().isEmpty())
            allExisting.add(medication);
        refresh();
    }

    // TODO uzupelnic jesli bedzie trzeba
    @FXML
    private void handleFilterAction(ActionEvent event) {
        medicationAppController.showMedicationFilterDialog(this, filterOptions);
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

    @FXML
    protected void handleExitAction(ActionEvent event) {
        medicationAppController.getPrimaryStage().close();
        medicationAppController.getAppController().showAdminPanel();
    }

    public void setData() {
        allExisting = FXCollections.observableArrayList(medicationAppController.getMedicationDAO().findAll());
        System.out.println(allExisting);
        medicationTableView.refresh();
        medicationTableView.setItems(allExisting.filtered(medication -> filter.matches(medication)));
    }

    private void clearFilterOptions() {
        filterOptions = new Object[7];
        Arrays.fill(filterOptions, null);
        filter = new DefaultMedicationFilter();
    }

    public void setFilterOptions(Object[] filterOptions) {
        if (filterOptions == null) throw new NullPointerException("Tablica opcji filtrow nie istnieje");
        this.filterOptions = filterOptions;

        MedicationFilter[] filters = {
                new NameMedicationFilter((String) filterOptions[0]),
                new FormMedicationFilter((MedicationForm) filterOptions[1]),
                new PrescriptionMedicationFilter((Boolean) filterOptions[2]),
                new PriceMedicationFilter((BigDecimal) filterOptions[3], (BigDecimal) filterOptions[4]),
                new QuantityMedicationFilter((Integer) filterOptions[5], (Integer) filterOptions[6])
        };
        filter = new CompositeMedicationFilter(filters);

        medicationTableView.setItems(allExisting.filtered(medication -> filter.matches(medication)));
    }

    void refresh() {
        medicationTableView.refresh();
    }

    public void setMedicationAppController(MedicationAppController medicationAppController) {
        this.medicationAppController = medicationAppController;
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
