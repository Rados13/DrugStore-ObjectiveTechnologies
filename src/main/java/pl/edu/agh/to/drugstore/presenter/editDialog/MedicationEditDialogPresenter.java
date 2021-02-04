package pl.edu.agh.to.drugstore.presenter.editDialog;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import pl.edu.agh.to.drugstore.model.medications.Medication;
import pl.edu.agh.to.drugstore.model.medications.MedicationForm;

import java.math.BigDecimal;

/**
 * Klasa odpowiadająca z wyświetlenie okna edycji wybranego leku w interfejsie graficznym
 */
public class MedicationEditDialogPresenter extends EditDialogPresenter {

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

    @FXML
    public void initialize() {
        formComboBox.getItems().addAll(
                FXCollections.observableArrayList(
                        MedicationForm.class.getEnumConstants()
                ));
    }

    public void setData(Medication medication) {
        this.medication = medication;
        updateControls();
    }

    protected void updateModel() {
        medication.setName(nameTextField.getText());
        medication.setForm(formComboBox.getValue());
        medication.setPrescriptionRequired(prescriptionRequiredCheckBox.isSelected());
        medication.setPrice(BigDecimal.valueOf(Double.parseDouble(priceTextField.getText())));
        medication.setQuantity(Integer.parseInt(quantityTextField.getText()));
    }

    protected void updateControls() {
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
