package pl.edu.agh.to.drugstore.presenter;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.edu.agh.to.drugstore.controller.AppController;
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

    private final static Logger logger = LoggerFactory.getLogger(AdminPanelPresenter.class);
    private Stage dialogStage;
    private Stage appStage;
    private EntityManager em;
    private final boolean approved = false;
    private AppController appController;

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
        ClientOrderAppController clientOrderAppController = new ClientOrderAppController(appStage, em, appController);
        clientOrderAppController.initRootLayout();
    }

    @FXML
    private void handleMedicationAction(ActionEvent event) throws IOException {
        dialogStage.close();
        MedicationAppController medicationAppController = new MedicationAppController(appStage, em, appController);
        medicationAppController.initRootLayout();
    }

    @FXML
    private void handlePersonAction(ActionEvent event) throws IOException {
        dialogStage.close();
        PersonAppController personAppController = new PersonAppController(appStage, em, appController);
        personAppController.initRootLayout();
    }

    @FXML
    private void handleSupplierAction(ActionEvent event) throws IOException {
        dialogStage.close();
        SupplierAppController supplierAppController = new SupplierAppController(appStage, em, appController);
        supplierAppController.initRootLayout();
    }

    @FXML
    private void handleExitAction(ActionEvent event) throws Exception {
        showConfirmationAlert();
    }

    public void setEntityManager(EntityManager em) {
        this.em = em;
    }


    private void showConfirmationAlert() throws Exception {
        Alert alert = Alerts.showConfirmationDialog("Confirmation Dialog", null, "Are you sure?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            alert.close();
            dialogStage.close();
        } else {
            alert.close();
        }
    }

    public AppController getAppController() {
        return this.appController;
    }

    public void setAppController(AppController appController) {
        this.appController = appController;
    }
}
