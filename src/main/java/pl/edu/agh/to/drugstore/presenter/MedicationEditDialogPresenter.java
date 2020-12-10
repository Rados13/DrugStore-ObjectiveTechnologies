package pl.edu.agh.to.drugstore.presenter;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import pl.edu.agh.to.drugstore.model.medications.Medication;
import pl.edu.agh.to.drugstore.model.medications.MedicationForm;

import java.math.BigDecimal;

/**
 * Klasa odpowiadająca z wyświetlenie okna edycji wybranego leku w interfejsie graficznym
 */
public class MedicationEditDialogPresenter {

    private Medication medication;

    @FXML
    private TextField nameTextField;

    @FXML
    private ComboBox<MedicationForm> formComboBox;

    @FXML
    private CheckBox prescriptionRequiredCheckBox;

    @FXML
    private TextField priceTextField;

    @FXML
    private TextField quantityTextField;

    private Stage dialogStage;

    private boolean approved;

    @FXML
    public void initialize() {
        formComboBox.getItems().addAll(
                FXCollections.observableArrayList(
                        MedicationForm.class.getEnumConstants()
                ));
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public void setData(Medication medication) {
        this.medication = medication;
        updateControls();
    }

    public boolean isApproved() {
        return approved;
    }

    @FXML
    private void handleOkAction(ActionEvent event) {
        updateModel();
        approved = true;
        dialogStage.close();
    }

    @FXML
    private void handleCancelAction(ActionEvent event) {
        dialogStage.close();
    }

    private void updateModel() {
        medication.setName(nameTextField.getText());
        medication.setForm(formComboBox.getValue());
        medication.setPrescriptionRequired(prescriptionRequiredCheckBox.isSelected());
        medication.setPrice(BigDecimal.valueOf(Double.parseDouble(priceTextField.getText())));
        medication.setQuantity(Integer.parseInt(quantityTextField.getText()));
    }

    private void updateControls() {
        nameTextField.setText(medication.getName());
        if (medication.getForm() != null)
            formComboBox.setValue(medication.getForm());
        else formComboBox.setValue(null);
        prescriptionRequiredCheckBox.setSelected(medication.isPrescriptionRequired());
        if (medication.getPrice() != null)
            priceTextField.setText(medication.getPrice().toString());
        else priceTextField.setText("0");
        quantityTextField.setText(String.valueOf(medication.getQuantity()));
    }
}
