package pl.edu.agh.to.drugstore.presenter;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.edu.agh.to.drugstore.controller.clientOrder.ClientOrderAppController;
import pl.edu.agh.to.drugstore.controller.medication.MedicationAppController;
import pl.edu.agh.to.drugstore.controller.person.PersonAppController;
import pl.edu.agh.to.drugstore.controller.supplier.SupplierAppController;

import javax.persistence.EntityManager;
import java.io.IOException;
import java.util.Optional;

/**
 * Klasa odpowiadająca z wyświetlenie okna edycji wybranej osoby w interfejsie graficznym
 */
public class AdminPanelPresenter {

    private Stage dialogStage;
    private Stage appStage;
    private EntityManager em;
    private boolean approved = false;

    private final static Logger logger = LoggerFactory.getLogger(AdminPanelPresenter.class);

    @FXML
    public void initialize() {
    }

    public void setAppStage(Stage appStage) {
        this.appStage = appStage;
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public boolean isApproved() {
        return approved;
    }

    @FXML
    private void handleClientOrderAction(ActionEvent event) throws IOException {
        dialogStage.close();
        ClientOrderAppController clientOrderAppController = new ClientOrderAppController(appStage, em);
        clientOrderAppController.initRootLayout();
    }

    @FXML
    private void handleMedicationAction(ActionEvent event) throws IOException {
        dialogStage.close();
        MedicationAppController medicationAppController = new MedicationAppController(appStage, em);
        medicationAppController.initRootLayout();
    }

    @FXML
    private void handlePersonAction(ActionEvent event) throws IOException {
        dialogStage.close();
        PersonAppController personAppController = new PersonAppController(appStage, em);
        personAppController.initRootLayout();

    }

    @FXML
    private void handleSupplierAction(ActionEvent event) throws IOException {
        dialogStage.close();
        SupplierAppController supplierAppController = new SupplierAppController(appStage, em);
        supplierAppController.initRootLayout();

    }

    @FXML
    private void handleExitAction(ActionEvent event) {
        showConfirmationAlert();
    }

    public void setEntityManager(EntityManager em) {
        this.em = em;
    }


    private void showConfirmationAlert() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            alert.close();
            dialogStage.close();
            System.exit(0);
        } else {
            alert.close();
        }
    }
}
