package pl.edu.agh.to.drugstore.presenter;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import lombok.Data;
import pl.edu.agh.to.drugstore.model.business.Supplier;

@Data
public class SupplierEditDialogPresenter {

    private Supplier supplier;

    @FXML
    private TextField nameTextField;

    @FXML
    private TextField NIPTextField;

    private Stage dialogStage;

    private boolean approved;

    @FXML
    public void initialize() {
    }

    public void setData(Supplier supplier) {
        this.supplier = supplier;
        updateControls();
    }


    private void updateControls() {
        nameTextField.setText(supplier.getName());
        NIPTextField.setText(supplier.getNIP());
    }

    @FXML
    public void handleCancelAction(ActionEvent actionEvent) {
        dialogStage.close();
    }

    @FXML
    public void handleOkAction(ActionEvent actionEvent) {
        updateModel();
        approved = true;
        dialogStage.close();
    }

    private void updateModel() {
        supplier.setName(nameTextField.getText());
        supplier.setNIP(NIPTextField.getText());
    }
}
