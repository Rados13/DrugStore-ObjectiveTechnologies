package pl.edu.agh.to.drugstore.presenter;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import pl.edu.agh.to.drugstore.controller.medication.MedicationOverviewController;
import pl.edu.agh.to.drugstore.model.medications.MedicationForm;

import java.math.BigDecimal;
import java.util.Arrays;

// TODO

/**
 * Klasa odpowiadajaca za wyswietlanie okna opcji filtrowania lekow
 */
public class MedicationsFilterDialogPresenter {

    private final Object[] filterOptions = new Object[7];
    @FXML
    private TextField nameTextField;
    @FXML
    private ComboBox<MedicationForm> formComboBox;
    @FXML
    private ComboBox<Boolean> prescriptionRequiredComboBox;
    @FXML
    private TextField priceLowerBoundTextField;
    @FXML
    private TextField priceUpperBoundTextField;
    @FXML
    private TextField quantityLowerBoundTextField;
    @FXML
    private TextField quantityUpperBoundTextField;
    private Stage dialogStage;
    private MedicationOverviewController overviewController;

    @FXML
    public void initialize() {
        Arrays.fill(filterOptions, null);

        // TODO - lista opcji formy lekow;
        //  null oznacza dowolna wartosc
        MedicationForm[] temp = MedicationForm.class.getEnumConstants();
        MedicationForm[] medicationForms = new MedicationForm[temp.length + 1];
        medicationForms[0] = null;
        System.arraycopy(temp, 0, medicationForms, 1, temp.length);
        formComboBox.getItems().addAll(
                FXCollections.observableArrayList(medicationForms));

        Boolean[] prescriptionRequiredOptions = {null, false, true};
        prescriptionRequiredComboBox.getItems().addAll(
                FXCollections.observableArrayList(prescriptionRequiredOptions));
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public void setData(MedicationOverviewController overviewController, Object[] filterOptions) {
        this.overviewController = overviewController;

        System.arraycopy(filterOptions, 0, this.filterOptions, 0, filterOptions.length);

        // TODO
        //  przetestowac czy dziala jak nalezy
        nameTextField.setText(filterOptions[0] == null ? "" : (String) filterOptions[0]);
        formComboBox.getSelectionModel().select((MedicationForm) filterOptions[1]);
        prescriptionRequiredComboBox.getSelectionModel().select((Boolean) filterOptions[2]);
        priceLowerBoundTextField.setText(filterOptions[3] == null ? "" : String.valueOf(filterOptions[3]));
        priceUpperBoundTextField.setText(filterOptions[4] == null ? "" : String.valueOf(filterOptions[4]));
        quantityLowerBoundTextField.setText(filterOptions[5] == null ? "" : String.valueOf(filterOptions[5]));
        quantityUpperBoundTextField.setText(filterOptions[6] == null ? "" : String.valueOf(filterOptions[6]));
    }

    @FXML
    private void handleClearAction(ActionEvent event) {
        Arrays.fill(filterOptions, null);

        nameTextField.clear();
        formComboBox.getSelectionModel().clearSelection();
        prescriptionRequiredComboBox.getSelectionModel().clearSelection();
        priceLowerBoundTextField.clear();
        priceUpperBoundTextField.clear();
        quantityLowerBoundTextField.clear();
        quantityUpperBoundTextField.clear();
    }

    @FXML
    private void handleOkAction(ActionEvent event) {

        // TODO
        //  sprawdzic czy dziala
        filterOptions[0] = nameTextField.getText();
        filterOptions[1] = formComboBox.getSelectionModel().getSelectedItem();
        filterOptions[2] = prescriptionRequiredComboBox.getSelectionModel().getSelectedItem();
        try {
            filterOptions[3] = BigDecimal.valueOf(Double.parseDouble(priceLowerBoundTextField.getText()));
        } catch (Exception e) {
            filterOptions[3] = null;
        }
        try {
            filterOptions[4] = BigDecimal.valueOf(Double.parseDouble(priceUpperBoundTextField.getText()));
        } catch (Exception e) {
            filterOptions[4] = null;
        }
        try {
            filterOptions[5] = Integer.parseInt(quantityLowerBoundTextField.getText());
        } catch (Exception e) {
            filterOptions[5] = null;
        }
        try {
            filterOptions[6] = Integer.parseInt(quantityUpperBoundTextField.getText());
        } catch (Exception e) {
            filterOptions[6] = null;
        }

        // TODO
        //  wyslac tablice 'filterOptions' do MedicationAppController
        overviewController.setFilterOptions(filterOptions);

        dialogStage.close();
    }

    @FXML
    private void handleCancelAction(ActionEvent event) {
        dialogStage.close();
    }

}
