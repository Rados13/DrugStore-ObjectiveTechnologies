package pl.edu.agh.to.drugstore.presenter;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;
import pl.edu.agh.to.drugstore.model.business.ClientOrder;
import pl.edu.agh.to.drugstore.model.business.Tuple;
import pl.edu.agh.to.drugstore.model.dao.MedicationDAO;
import pl.edu.agh.to.drugstore.model.dao.PersonDAO;
import pl.edu.agh.to.drugstore.model.medications.Medication;
import pl.edu.agh.to.drugstore.model.people.Person;
import pl.edu.agh.to.drugstore.model.people.Role;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;

/**
 * Klasa odpowiadająca z wyświetlenie okna edycji wybranego zamówienia klienta w interfejsie graficznym
 */

@Setter
@Getter
public class MyOrdersEditDialogPresenter {

    private ClientOrder clientOrder;

    private PersonDAO personDAO;

    private MedicationDAO medicationDAO;

    private ObservableList<Tuple> allOrderElems;

    private ObservableList<Medication> medicationsList;

    private FilteredList<Medication> medicationsNames;

    @FXML
    private DatePicker submissionDatePicker;

    @FXML
    private DatePicker shippingDatePicker;

    @FXML
    private ComboBox<Person> clientComboBox;

    @FXML
    private TableView<Tuple> orderElemsTableView;

    @FXML
    private TableColumn<Tuple, String> medicationNameColumn;

    @FXML
    private TableColumn<Tuple, BigDecimal> medicationPriceColumn;

    @FXML
    private TableColumn<Tuple, Integer> amountOfMedicationBoxesColumn;

    @FXML
    private ComboBox<Medication> medicationComboBox;

    @FXML
    private TextField amountBoxesTextField;

    private Stage dialogStage;

    private boolean approved;

    @FXML
    public void initialize() {
        clientComboBox.getItems().addAll(
                FXCollections.observableArrayList());
        orderElemsTableView.getSelectionModel().setSelectionMode(
                SelectionMode.MULTIPLE);

        medicationNameColumn.setCellValueFactory(dataValue -> dataValue.getValue().getMedication().getNameProperty());
        medicationPriceColumn.setCellValueFactory(dataValue -> dataValue.getValue().getMedication().getPriceProperty());
        amountOfMedicationBoxesColumn.setCellValueFactory(dataValue -> dataValue.getValue().getQuantityProperty());

        medicationComboBox.setEditable(true);
        medicationComboBox.getEditor().textProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue.length() >= oldValue.length()) {
                final TextField editor = medicationComboBox.getEditor();
                final Medication tmp = medicationComboBox.getSelectionModel().getSelectedItem();
                final String selected = tmp != null ? tmp.toString() : null;
                if (selected != null && selected.equals(editor.getText())) return;
            } else {
                medicationComboBox.valueProperty().set(null);
                medicationComboBox.getEditor().setText(newValue);
            }
            Platform.runLater(() -> {
                medicationsNames.setPredicate(item -> item.getName().toUpperCase().startsWith(newValue.toUpperCase()));
            });
        });
    }

    public void setData(ClientOrder clientOrder) {
        this.clientOrder = clientOrder;
        allOrderElems = FXCollections.observableArrayList(clientOrder.getMedications());
        orderElemsTableView.setItems(allOrderElems);
        orderElemsTableView.refresh();
        updateControls();
    }


    public void updateClientComboBox() {
        clientComboBox.getItems().addAll(
                FXCollections.observableArrayList(
                        personDAO.searchPeople(Person.builder().role(Role.CLIENT).build())));
        medicationsList = FXCollections.observableArrayList(medicationDAO.findAll());
        medicationsNames = new FilteredList<Medication>(medicationsList, p -> true);
        medicationComboBox.setItems(medicationsNames);
    }

    @FXML
    private void handleOkAction(ActionEvent event) {
        if (clientComboBox.getValue() != null) {
            updateModel();
            approved = true;
            dialogStage.close();
        }
    }

    @FXML
    private void handleCancelAction(ActionEvent event) {
        dialogStage.close();
    }

    @FXML
    private void handleAddAction(ActionEvent event) {
        if (medicationsNames.size() > 0) {
            Optional<Medication> optionalMedication = medicationsNames.stream()
                    .filter(elem -> elem.toString().equals(medicationComboBox.getEditor().getText())).findFirst();
            if (optionalMedication.isEmpty()) return;
            Medication med = optionalMedication.get();
            int boxesAmount = Integer.parseInt(amountBoxesTextField.getText());
            allOrderElems.add(new Tuple(boxesAmount, false, med));
            orderElemsTableView.refresh();
            medicationComboBox.valueProperty().set(null);
            amountBoxesTextField.setText("");
        }
    }

    @FXML
    private void handleDeleteAction(ActionEvent event) {
        ObservableList<Tuple> elemsToDelete = orderElemsTableView.getSelectionModel().getSelectedItems();
        allOrderElems.removeAll(elemsToDelete);
    }

    private void updateModel() {
        clientOrder.setShippingDate(java.sql.Date.valueOf(shippingDatePicker.getValue()));
        clientOrder.setSubmissionDate(java.sql.Date.valueOf(submissionDatePicker.getValue()));
        clientOrder.setPerson(clientComboBox.getValue());
        clientOrder.updateMedications(allOrderElems);
    }

    private LocalDate changeDateToLocalDate(Date date) {
        if (date.getClass() == java.sql.Date.class) return ((java.sql.Date) date).toLocalDate();
        return LocalDate.ofInstant(clientOrder.getSubmissionDate().toInstant(), ZoneId.systemDefault());
    }

    private void updateControls() {
        if (clientOrder.getSubmissionDate() != null)
            submissionDatePicker.setValue(changeDateToLocalDate(clientOrder.getSubmissionDate()));
        else submissionDatePicker.setValue(LocalDate.now());

        if (clientOrder.getShippingDate() != null)
            shippingDatePicker.setValue(changeDateToLocalDate(clientOrder.getShippingDate()));
        else shippingDatePicker.setValue(LocalDate.now());

        if (clientOrder.getPerson() != null)
            clientComboBox.setValue(clientOrder.getPerson());
        else clientComboBox.setValue(new Person());

    }

}
