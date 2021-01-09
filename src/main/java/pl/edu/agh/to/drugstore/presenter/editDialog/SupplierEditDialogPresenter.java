package pl.edu.agh.to.drugstore.presenter.editDialog;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import lombok.Data;
import pl.edu.agh.to.drugstore.model.business.Supplier;

@Data
public class SupplierEditDialogPresenter extends EditDialogPresenter {

    private Supplier supplier;

    @FXML
    private TextField nameTextField;

    @FXML
    private TextField NIPTextField;

    @FXML
    public void initialize() {
    }

    public void setData(Supplier supplier) {
        this.supplier = supplier;
        updateControls();
    }


    protected void updateControls() {
        nameTextField.setText(supplier.getName());
        NIPTextField.setText(supplier.getNIP());
    }

    protected void updateModel() {
        supplier.setName(nameTextField.getText());
        supplier.setNIP(NIPTextField.getText());
    }
}
